package dan.example.dan_financial_book.calendar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarDto {
    private String date;
    private boolean holiYn;
    private String holiName;
    private int expense;
    private int income;
    private int transfer;
}
