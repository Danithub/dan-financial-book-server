package dan.example.dan_financial_book.calendar.controller;

import dan.example.dan_financial_book.calendar.service.CalendarService;
import dan.example.dan_financial_book.common.ResponseEntity;
import dan.example.dan_financial_book.common.service.ResponseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cal")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {
    private final CalendarService calendarService;
    private final ResponseService responseService;

    @PutMapping("refresh")
    @ResponseBody
    public ResponseEntity<Integer> refresh(){
        log.debug("CalendarController refresh =============> Start");
        int res = calendarService.insertHolidays();
        return responseService.toResponseEntity("200", res);
    }
}
