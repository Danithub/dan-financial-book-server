package dan.example.dan_financial_book.calendar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dan.example.dan_financial_book.calendar.dao.HolidayDao;
import dan.example.dan_financial_book.calendar.dto.DateDto;
import dan.example.dan_financial_book.calendar.mapper.CalendarMapper;
import dan.example.dan_financial_book.calendar.utils.DateUtils;
import dan.example.dan_financial_book.common.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private static final String JSON = "json";
    private final CalendarMapper calendarMapper;
    private final WebClient webClient;
    @Value("${data.go.kr.openapi.holide.endpoint}")
    private String endpoint;
    @Value("${data.go.kr.openapi.servicekey.enc}")
    private String serviceKey;

    /**
     * 오늘(이번 달)을 기준으로 1년치의 휴일 정보를 비동기로 조회한다.
     *
     * @return Mono<OpenApiHolidayDto>
     */
    private List<Mono<String>> findHolidays() {

        List<Mono<String>> arr = new ArrayList<>();
        for (int i = -2; i < 12; i++) {
            DateDto date = DateUtils.toDateDtoInstance(DateUtils.getToday().plusMonths(i));

            // Service Key에 특수문자가 포함되어 있어어 인코딩이 불편하기 때문에 URI로 만들어서 활용.
            String url = endpoint +
                    String.format("?solYear=%s", date.getYears()) +
                    String.format("&solMonth=%s", String.format("%02d", date.getMonth())) +
                    String.format("&ServiceKey=%s", serviceKey) +
                    String.format("&_type=%s", JSON);
            //log.info("CalendarService findHolidays Url =============> {}", url);

            try {
                Mono<String> res = webClient.get()
                        .uri(new URI(url))
                        .retrieve()
                        .bodyToMono(String.class);

                arr.add(res);
            } catch (Exception e) {
                log.info("CalendarService findHolidays Error =============> {}", e.toString());
            }

        }

        return arr;
    }

    /**
     * 비동기로 조회한 특일 정보를 구독하여 정상적인 정보만 리턴한다.
     *
     * @param monos List<Mono<OpenApiHolidayDto>>
     * @return List<OpenApiHolidayDto>
     */
    private void InsertDataSubscribingMonoList(List<Mono<String>> monos) {
        // log.info("CalendarService InsertDataSubscribingMonoList Monos =============> {}", DataParsingUtils.convertObjectToJsonString(monos));

        for (Mono<String> mono : monos) {
            mono.subscribe(
                    success -> {
                        // 성공 시 처리
                        // String to HashMap
                        HashMap<String, Object> holidayMap = convertStringToHashMap(success);

                        // HashMap to Dao
                        List<HolidayDao> daoList = createHolidayDaoList(holidayMap);

                        // Insert Dao into Database
                        if (!daoList.isEmpty()) {
                            insertDaoList(daoList);
                        }
                    },
                    error -> {
                        // 오류 시 처리
                        log.info("CalendarService InsertDataSubscribingMonoList Error =============> {}", error.toString());
                    }
            );
        }
    }

    /**
     * HashMap 형식의 특일 정보 내 Items 값의 타입에 따라 분기처리
     *
     * @param holidayMap response
     * @return dao list
     */
    private List<HolidayDao> createHolidayDaoList(HashMap<String, Object> holidayMap) {
        // Items에 대한 분기처리 ("", {}, List)
        HashMap<String, Object> response = DataUtils.safeCastToMap(holidayMap.get("response"));
        HashMap<String, Object> body = DataUtils.safeCastToMap(response.get("body"));

        int totalCount = (int) body.get("totalCount");

        List<HolidayDao> daoList = new ArrayList<>();
        if (totalCount <= 0) {
            // 공휴일 없음.
            log.info("CalendarService InsertDataSubscribingMonoList Subscribe Holiday Zero =============> {}", body);
        }
        if (totalCount == 1) {
            // 공휴일 하루인 경우 객체 형태의 items가 들어옴.
            HashMap<String, Object> items = DataUtils.safeCastToMap(body.get("items"));
            HashMap<String, Object> item = DataUtils.safeCastToMap(items.get("item"));

            HolidayDao dao = HolidayDao.builder()
                    .locDate(String.valueOf(item.get("locdate")))
                    .dateName(String.valueOf(item.get("dateName")))
                    .holiYn(Objects.equals(item.get("isHoliday"), "Y"))
                    .build();
            daoList.add(dao);
        }
        if (totalCount > 1) {
            // 공휴일 둘 이상인 경우 List<객체> 형태의 items가 들어옴.
            HashMap<String, Object> items = DataUtils.safeCastToMap(body.get("items"));
            ArrayList<HashMap<String, Object>> item = DataUtils.safeCastToArrayList(items.get("item"));
            for (HashMap<String, Object> itemMap : item) {
                HolidayDao dao = HolidayDao.builder()
                        .locDate(String.valueOf(itemMap.get("locdate")))
                        .dateName(String.valueOf(itemMap.get("dateName")))
                        .holiYn(Objects.equals(itemMap.get("isHoliday"), "Y"))
                        .build();
                daoList.add(dao);
            }
        }

        return daoList;
    }

    /**
     * 문자열 형식의 데이터를 HashMap을 변환한다.
     *
     * @param jsonString json 형식의 특일 정보 문자열
     * @return HashMap
     */
    private HashMap<String, Object> convertStringToHashMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> holidayMap = null;
        try {
            holidayMap = (HashMap<String, Object>) mapper.readValue(jsonString, Map.class);
        } catch (Exception e) {
            log.info("CalendarService convertStringToHashMap Error =============> {}", e.toString());
        }

        return holidayMap;
    }

    /**
     * 특일 정보 DAO를 DB에 Insert한다.
     *
     * @param list<HolidayDao> HolidayDaoList
     */
    private void insertDaoList(List<HolidayDao> list) {
        int res = calendarMapper.insertHolidays(list);
        log.info("CalendarService insertDaoList Success =============> {}", res);
    }

    public void insertHolidays() {
        // 특일 정보 조회
        List<Mono<String>> holiDtoMonoList = findHolidays();

        // 특일 정보 업데이트
        InsertDataSubscribingMonoList(holiDtoMonoList);
    }
}
