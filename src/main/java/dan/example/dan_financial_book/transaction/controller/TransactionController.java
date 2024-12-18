package dan.example.dan_financial_book.transaction.controller;

import dan.example.dan_financial_book.common.ResponseEntity;
import dan.example.dan_financial_book.common.service.ResponseService;
import dan.example.dan_financial_book.transaction.dto.TransactionDto;
import dan.example.dan_financial_book.transaction.dto.TxnPeriodRequestDto;
import dan.example.dan_financial_book.transaction.dto.TxnPeriodResponseDto;
import dan.example.dan_financial_book.transaction.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/txn")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;
    private final ResponseService responseService;

    @GetMapping("test")
    @ResponseBody
    public ResponseEntity<Object> checkConnection(){
        return responseService.toResponseEntity("200", "OK");
    }

    @GetMapping("add")
    @ResponseBody
    public ResponseEntity<Integer> addTransaction(HttpServletRequest request){
        int res = transactionService.addTransaction(request);
        log.debug("res = {}", res);
        return responseService.toResponseEntity("200", res);
    }

    @GetMapping("find")
    @ResponseBody
    public ResponseEntity<List<TransactionDto>> findTransaction(HttpServletRequest request){
        String date = request.getParameter("date");
        log.debug("date = {}", date);

        List<TransactionDto> result = transactionService.findTransactionByDate(date);

        return responseService.toResponseEntity("200", result);
    }

    @PostMapping("find/period")
    @ResponseBody
    public ResponseEntity<TxnPeriodResponseDto> findTxnForPeriod(@RequestBody TxnPeriodRequestDto request){
        log.debug("findTxnForPeriod Req Dto========> {} ", request);

        TxnPeriodResponseDto result = transactionService.findTxnForPeriod(request);

        return responseService.toResponseEntity("200", result);
    }
}
