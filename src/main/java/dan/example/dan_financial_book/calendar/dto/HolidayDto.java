package dan.example.dan_financial_book.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class HolidayDto {
    private List<Holiday> holidays;

    private static class Holiday {
        private String dateKind;
        private String dateName;
        private String isHoliday;
        private Long locdate;
        private Long seq;
    }
}
