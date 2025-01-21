package dan.example.dan_financial_book.calendar.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class HolidayDao {
    private String locDate;
    private String dateName;
    private boolean holiYn;
    private String crtTime;
    private String uptTime;
}
