package dan.example.dan_financial_book.transaction.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 거래 정보 DAO
@Getter
@Setter
@ToString
@Builder
public class TransactionDao {
    protected Long tr_id;
    protected String tr_type;
    protected String category;
    protected int amount;
    protected String contents;
    protected String memo;
    protected String tr_date;
    protected String crt_time;
    protected String upt_time;
}
