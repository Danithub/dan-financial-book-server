package dan.example.dan_financial_book.transaction.mapper;

import dan.example.dan_financial_book.transaction.dto.TransactionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper {
    public void findTransactionByDate(String date);

    public void addTransaction(TransactionDto transaction);
}
