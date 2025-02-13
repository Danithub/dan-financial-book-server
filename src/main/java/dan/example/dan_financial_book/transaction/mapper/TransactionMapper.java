package dan.example.dan_financial_book.transaction.mapper;

import dan.example.dan_financial_book.transaction.dao.TransactionDao;
import dan.example.dan_financial_book.transaction.dao.TxnPeriodDao;
import dan.example.dan_financial_book.transaction.dto.TxnPeriodRequestDto;
import dan.example.dan_financial_book.transaction.dto.TxnPeriodResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TransactionMapper {
    public List<TransactionDao> findTransactionByDate(String date);

    public int addTransaction(TransactionDao transaction);

    public List<TxnPeriodDao> findTxnForPeriod(TxnPeriodRequestDto request);
}
