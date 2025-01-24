package dan.example.dan_financial_book.calendar.mapper;

import dan.example.dan_financial_book.calendar.dao.HolidayDao;
import dan.example.dan_financial_book.calendar.dto.CalendarDto;
import dan.example.dan_financial_book.calendar.dto.CalendarReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CalendarMapper {
    public int insertHolidays(List<HolidayDao> list);

    public List<CalendarDto> findCalendarByMonth(CalendarReqDto request);
}
