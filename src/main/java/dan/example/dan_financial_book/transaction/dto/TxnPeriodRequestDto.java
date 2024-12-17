package dan.example.dan_financial_book.transaction.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class TxnPeriodRequestDto {
    private String stdt;
    private String eddt;
}
