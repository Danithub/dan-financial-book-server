package dan.example.dan_financial_book.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseEntity<T> {

    private ResponseHeader header;
    private T body;

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("ResponseEntity : {");
        if (header != null) {
            sb.append(header.toString());
        } else {
            sb.append("headerVo : null");
        }
        sb.append(", ");
        if (body != null) {
            sb.append("body : {").append(body.toString()).append("}");
        } else {
            sb.append("body : null");
        }
        sb.append("}");

        return sb.toString();
    }
}
