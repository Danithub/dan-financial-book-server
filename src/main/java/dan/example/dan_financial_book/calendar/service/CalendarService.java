package dan.example.dan_financial_book.calendar.service;

import dan.example.dan_financial_book.calendar.dao.OpenApiHolidayDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
    private final WebClient webClient;
    private static final String JSON = "json";

    @Value("${data.go.kr.openapi.holide.endpoint}")
    private String endpoint;
    @Value("${data.go.kr.openapi.servicekey.enc}")
    private String serviceKey;

    /**
     * 공공 API를 통한 공휴일 조회 기능
     * 특정 기간 동안의 공휴일 정보를 전부 가져온다.
     * @param stdt YYYYMMDD
     * @param eddt YYYYMMDD
     * @return json
     */
    private OpenApiHolidayDao findHolidays(String stdt, String eddt){

        /**
         * TODO 특정 기간의 개월 수 만큼 반복하여 각 월의 공휴일을 가져오고 총합을 리턴한다.
         */

        String year = "2024";
        String month = "10";
        String url = endpoint +
                String.format("?solYear=%s", year) +
                String.format("&solMonth=%s", month) +
                String.format("&ServiceKey=%s", serviceKey) +
                String.format("&_type=%s", JSON);

        OpenApiHolidayDao res = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(OpenApiHolidayDao.class).block();

        log.info("res : {}", res);
        return res;
    }
}
