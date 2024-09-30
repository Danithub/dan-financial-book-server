package dan.example.dan_financial_book.common.service;

import dan.example.dan_financial_book.common.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseService {
    public <T> ResponseEntity<T> toResponseEntity(String code, String[] arguments, T body) {
        return ResponseEntity.<T>builder().header(dan.example.dan_financial_book.common.ResponseHeader.builder().code(getCode(code)).message(getMessage(code, arguments)).build()).body(body).build();
    }

    public <T> ResponseEntity<T> toResponseEntity(String code, T body) {
        return ResponseEntity.<T>builder().header(dan.example.dan_financial_book.common.ResponseHeader.builder().code(getCode(code)).message(getMessage(code, null)).build()).body(body).build();
    }

    public <T> ResponseEntity<T> toResponseEntity(String code, String[] arguments, String origin, T body) {
        return ResponseEntity.<T>builder().header(dan.example.dan_financial_book.common.ResponseHeader.builder().code(getCode(code)).message(getMessage(code, arguments)).origin(origin).build()).body(body).build();
    }

    private String getCode(String code) {
        return code;
    }

    private String getMessage(String code, String[] arguments) {
        return code;
    }
}
