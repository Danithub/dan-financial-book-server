package dan.example.dan_financial_book.calendar.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DateDto {
    private int years;
    private int month;
    private int day;
}
