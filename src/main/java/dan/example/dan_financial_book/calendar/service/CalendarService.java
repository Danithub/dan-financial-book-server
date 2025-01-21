package dan.example.dan_financial_book.calendar.service;

import dan.example.dan_financial_book.calendar.dao.HolidayDao;
import dan.example.dan_financial_book.calendar.dto.DateDto;
import dan.example.dan_financial_book.calendar.dto.OpenApiHolidayDto;
import dan.example.dan_financial_book.calendar.dto.OpenApiHolidayDto.Response.Body.Items.Item;
import dan.example.dan_financial_book.calendar.mapper.CalendarMapper;
import dan.example.dan_financial_book.calendar.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
     * 오늘(이번 달)을 기준으로 1년치의 휴일 정보를 리턴한다.
     *
     * @return List<OpenApiHolidayDao>
     */
    private List<OpenApiHolidayDto> findHolidays() {

        List<OpenApiHolidayDto> arr = new ArrayList<>();
        for (int i = -2; i < 12; i++) {
            DateDto date = DateUtils.toDateDtoInstance(DateUtils.getToday().plusMonths(i));
            log.info("CalendarService findHolidays date =============> {}", date);

            String url = endpoint +
                    String.format("?solYear=%s", date.getYears()) +
                    String.format("&solMonth=%s", date.getMonth()) +
                    String.format("&ServiceKey=%s", serviceKey) +
                    String.format("&_type=%s", JSON);
            log.info("CalendarService findHolidays url =============> {}", url);

            Optional<OpenApiHolidayDto.Response.Body> responseBody = webClient.get()
                    .uri(url)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(OpenApiHolidayDto.Response.Body.class)
                    .flux()
                    .toStream()
                    .findFirst();

//            OpenApiHolidayDto res = restTemplate.getForObject(url, OpenApiHolidayDto.class);

//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Accept", "application/json"); // JSON 응답 요청
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            log.info("CalendarService findHolidays res =============> {}", restTemplate.exchange(url, HttpMethod.GET, entity, OpenApiHolidayDto.class));

            //arr.add(res);
        }

        return arr;
    }

    /**
     * 공공 API를 통한 공휴일 조회 기능
     * 특정 기간 동안의 공휴일 정보를 전부 가져온다.
     *
     * @param stdt YYYYMMDD
     * @param eddt YYYYMMDD
     * @return json
     */
    private OpenApiHolidayDto findHolidays(String stdt, String eddt) {

        /**
         * TODO 특정 기간의 개월 수 만큼 반복하여 각 월의 공휴일을 가져오고 총합을 리턴한다.
         */
        DateDto dateSpan = DateUtils.getTimeSpan(stdt, eddt);
        log.info("TimeSpan =======> {}", dateSpan);

        String year = "2024";
        String month = "10";
        String url = endpoint +
                String.format("?solYear=%s", year) +
                String.format("&solMonth=%s", month) +
                String.format("&ServiceKey=%s", serviceKey) +
                String.format("&_type=%s", JSON);

        OpenApiHolidayDto res = null;
//        webClient.get()
//                .uri(url)
//                .retrieve()
//                .bodyToMono(OpenApiHolidayDto.class).block();

        log.info("res : {}", res);
        return res;
    }

    public int insertHolidays() {
        List<OpenApiHolidayDto> holiDtoList = findHolidays();
        List<HolidayDao> holiDaoList = new ArrayList<>();

        // 각 월의 공휴일 정보를 반복
        for (OpenApiHolidayDto element : holiDtoList) {
            List<Item> holidays = element.getResponse().getBody().getItems().getItem();

            // 공휴일을 Dao List에 추가한다.
            for (Item holiday : holidays) {
                holiDaoList.add(HolidayDao.builder()
                        .locDate(String.valueOf(holiday.getLocdate()))
                        .dateName(holiday.getDateName())
                        .holiYn(Objects.equals(holiday.getIsHoliday(), "Y"))
                        .build());
            }
        }
        log.info("CalendarService insertHolidays holiDaoList =============> {}", holiDaoList);

        return calendarMapper.insertHolidays(holiDaoList);
    }
}
