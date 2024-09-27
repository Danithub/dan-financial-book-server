package dan.example.dan_financial_book.transaction.controller;

import dan.example.dan_financial_book.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @RequestMapping("add")
    @ResponseBody
    public String addTransaction(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();
        System.out.println("requestURL = " + requestURL);

        //transactionService.addTransaction(null);
        return "OK";
    }

    @RequestMapping("find")
    @ResponseBody
    public String findTransaction(HttpServletRequest request){
        String requestURL = request.getRequestURL().toString();

        //transactionService.getTransactionByDate(null);
        return "OK";
    }
}
