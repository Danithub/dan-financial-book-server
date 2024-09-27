package dan.example.dan_financial_book.transaction.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 거래 정보 DAO
@Getter
@Setter
@ToString
public class TransactionDao {
    protected Long tr_id;
    protected String type;
    protected int amount;
    protected String content;
    protected String category;
    protected String date;
    protected String crt_time;
    protected String upt_time;
}
