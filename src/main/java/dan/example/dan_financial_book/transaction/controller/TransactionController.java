package dan.example.dan_financial_book.transaction.controller;

import dan.example.dan_financial_book.common.ResponseEntity;
import dan.example.dan_financial_book.common.service.ResponseService;
import dan.example.dan_financial_book.transaction.dto.TransactionDto;
import dan.example.dan_financial_book.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final ResponseService responseService;

    @RequestMapping("add")
    @ResponseBody
    public ResponseEntity<Integer> addTransaction(HttpServletRequest request){
        int res = transactionService.addTransaction(request);
        System.out.println("res = " + res);
        return responseService.toResponseEntity("200", res);
    }

    @RequestMapping("find")
    @ResponseBody
    public ResponseEntity<List<TransactionDto>> findTransaction(HttpServletRequest request){
        String date = request.getParameter("date");
        System.out.println("date = " + date);

        List<TransactionDto> result = transactionService.findTransactionByDate(date);

        return responseService.toResponseEntity("200", result);
    }
}
