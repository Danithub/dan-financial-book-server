package dan.example.dan_financial_book.transaction.service;

import dan.example.dan_financial_book.transaction.dto.TransactionDto;
import dan.example.dan_financial_book.transaction.mapper.TransactionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    public TransactionDto getTransactionByDate(String date){
        return null;
    }

    List<TransactionDto> getTransactionHistory(String date){
        return null;
    }

    public void addTransaction(TransactionDto transaction){

    }

    void updateTransaction(TransactionDto transaction){

    }

    void deleteTransaction(Long id){

    }
}
