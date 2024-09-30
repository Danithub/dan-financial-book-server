package dan.example.dan_financial_book.transaction.dto;

import dan.example.dan_financial_book.common.TransactionType;
import lombok.*;

// 거래 정보 DTO
@Getter
@Setter
@ToString
@Builder
public class TransactionDto {
    // id
    protected Long id;
    // 타입
    protected TransactionType type;
    // 날짜
    protected String date;
    // 거래 금액
    protected int amount;
    // 내용
    protected String content;
    // 카테고리
    protected String category;
    // 메모
    protected String memo;
}
