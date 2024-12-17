package dan.example.dan_financial_book.transaction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
public class TxnPeriodResponseDto {
    private List<Day> days;

    @Getter
    @Setter
    @Builder
    public static class Day {
        private String holiday;
        private String date;
        private int income;
        private int expense;
        private int transfer;
    }
}
