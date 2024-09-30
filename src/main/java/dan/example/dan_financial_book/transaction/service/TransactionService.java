package dan.example.dan_financial_book.transaction.service;

import dan.example.dan_financial_book.common.TransactionType;
import dan.example.dan_financial_book.transaction.dao.TransactionDao;
import dan.example.dan_financial_book.transaction.dto.TransactionDto;
import dan.example.dan_financial_book.transaction.mapper.TransactionMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionMapper transactionMapper;

    public List<TransactionDto> findTransactionByDate(String date){
        List<TransactionDao> list = transactionMapper.findTransactionByDate(date);
        List<TransactionDto> trList = new ArrayList<>();
        for (TransactionDao dao : list) {
            trList.add(TransactionDto.builder()
                    .id(dao.getTr_id())
                    .type(Objects.equals(dao.getTr_type(), "0") ? TransactionType.EXPENSE : TransactionType.INCOME)
                    .date(dao.getTr_date())
                    .amount(dao.getAmount())
                    .content(dao.getContents())
                    .category(dao.getCategory())
                    .memo(dao.getMemo())
                    .build());
        }
        return trList;
    }

    List<TransactionDto> getTransactionHistory(String date){
        return null;
    }

    public int addTransaction(HttpServletRequest request){

        // 타입, 카테고리, 금액, 내용
        String type = request.getParameter("type");
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");
        String content = request.getParameter("content");

        TransactionDao dao = TransactionDao.builder()
                .tr_type(type)
                .amount(Integer.parseInt(amount))
                .category(category)
                .contents(content)
                .build();

        return transactionMapper.addTransaction(dao);
    }

    void updateTransaction(TransactionDto transaction){

    }

    void deleteTransaction(Long id){

    }
}
