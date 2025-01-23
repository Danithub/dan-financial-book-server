package dan.example.dan_financial_book.calendar.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dan.example.dan_financial_book.calendar.dao.HolidayDao;
import dan.example.dan_financial_book.calendar.dto.DateDto;
import dan.example.dan_financial_book.calendar.dto.OpenApiHolidayDto;
import dan.example.dan_financial_book.calendar.dto.OpenApiHolidayDto.Response.Body.Items.Item;
import dan.example.dan_financial_book.calendar.mapper.CalendarMapper;
import dan.example.dan_financial_book.calendar.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private static final String JSON = "json";
    private final CalendarMapper calendarMapper;
    private final WebClient webClient;
    //    private final RestTemplate restTemplate;
    @Value("${data.go.kr.openapi.holide.endpoint}")
    private String endpoint;
    @Value("${data.go.kr.openapi.servicekey.enc}")
    private String serviceKey;

    /**
     * 오늘(이번 달)을 기준으로 1년치의 휴일 정보를 비동기로 조회한다.
     *
     * @return Mono<OpenApiHolidayDto>
     */
    private List<Mono<OpenApiHolidayDto>> findHolidays() {

        List<Mono<OpenApiHolidayDto>> arr = new ArrayList<>();
        for (int i = -2; i < 12; i++) {
            DateDto date = DateUtils.toDateDtoInstance(DateUtils.getToday().plusMonths(i));

            String url = endpoint +
                    String.format("?solYear=%s", date.getYears()) +
                    String.format("&solMonth=%s", date.getMonth()) +
                    String.format("&ServiceKey=%s", serviceKey) +
                    String.format("&_type=%s", JSON);

            try {
                Mono<OpenApiHolidayDto> res = webClient.get()
                        .uri(new URI(url))
                        .retrieve()
                        .bodyToMono(OpenApiHolidayDto.class);

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
    private void InsertDataSubscribingMonoList(List<Mono<OpenApiHolidayDto>> monos) {
        // log.info("CalendarService InsertDataSubscribingMonoList Monos =============> {}", DataParsingUtils.convertObjectToJsonString(monos));

        for (Mono<OpenApiHolidayDto> mono : monos) {
            mono.subscribe(
                    success -> {
                        // 성공 시 처리
                        //log.info("CalendarService InsertDataSubscribingMonoList subscribe sucess =============> {}", DataParsingUtils.convertObjectToJsonString(success));
                        List<HolidayDao> daoList = convertHolidaysToDaoList(success);
                        insertDaoList(daoList);
                    },
                    error -> {
                        // 오류 시 처리
                        log.info("CalendarService InsertDataSubscribingMonoList Error =============> {}", error.toString());
                    }
            );
        }
    }

    /**
     * 조회한 특일 정보를 DAO로 변환한다.
     *
     * @param dto 특일 정보
     * @return dao list
     */
    private List<HolidayDao> convertHolidaysToDaoList(OpenApiHolidayDto dto) {
        // LinkedHashMap 형태의 items 객체를 Json Serialize를 통해서 List로 바꾸는데,
        // 바로 List 변수로 할당할 경우 ClassCastException가 발생함.
        // ObjectMapper.convertValue()를 통해 깔끔하게 Casting을 진행하는 듯함.
        ObjectMapper mapper = new ObjectMapper();
        List<Item> holidays = mapper.convertValue(dto.getResponse().getBody().getItems().getItem(), new TypeReference<>() {
        });

        List<HolidayDao> list = new ArrayList<>();
        for (Item holiday : holidays) {
            HolidayDao dao = HolidayDao.builder()
                    .locDate(String.valueOf(holiday.getLocdate()))
                    .dateName(holiday.getDateName())
                    .holiYn(Objects.equals(holiday.getIsHoliday(), "Y"))
                    .build();
            list.add(dao);
        }

        //log.info("CalendarService convertHolidaysToDaoList list =============> {}", DataParsingUtils.convertObjectToJsonString(list));
        return list;
    }

    /**
     * 특일 정보 DAO를 DB에 Insert한다.
     *
     * @param List<HolidayDao> HolidayDao
     */
    private void insertDaoList(List<HolidayDao> list) {
        log.info("CalendarService insertDaoList Success =============> {}", list);

        //calendarMapper.insertHolidays(list);
    }

    public void insertHolidays() {
        // 특일 정보 조회
        List<Mono<OpenApiHolidayDto>> holiDtoMonoList = findHolidays();

        // 특일 정보 업데이트
        InsertDataSubscribingMonoList(holiDtoMonoList);
    }
}
