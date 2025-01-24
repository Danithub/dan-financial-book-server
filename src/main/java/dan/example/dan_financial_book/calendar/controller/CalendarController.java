package dan.example.dan_financial_book.calendar.controller;

import dan.example.dan_financial_book.calendar.dto.CalendarDto;
import dan.example.dan_financial_book.calendar.dto.CalendarReqDto;
import dan.example.dan_financial_book.calendar.service.CalendarService;
import dan.example.dan_financial_book.common.ResponseEntity;
import dan.example.dan_financial_book.common.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/cal")
@RequiredArgsConstructor
@Slf4j
public class CalendarController {
    private final ResponseService responseService;
    private final CalendarService calendarService;


    @PutMapping("refresh")
    @ResponseBody
    public ResponseEntity<String> refresh(){
        log.info("CalendarController refresh =============> Start");
        calendarService.insertHolidays();
        return responseService.toResponseEntity("200", "Success");
    }

    @PostMapping("find")
    @ResponseBody
    public ResponseEntity<List<CalendarDto>> findCalendarByMonth(@RequestBody CalendarReqDto request){
        log.info("CalendarController find =============> Start");
        List<CalendarDto> res = calendarService.findCalendarByMonth(request);
        return responseService.toResponseEntity("200", res);
    }
}
