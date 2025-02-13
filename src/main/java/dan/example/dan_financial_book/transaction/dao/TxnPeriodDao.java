package dan.example.dan_financial_book.transaction.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 기간 거래 정보 Dao
@Getter
@Setter
@ToString
@Builder
public class TxnPeriodDao {
    private String trDate;
    private int expense;
    private int income;
    private int transfer;
}
