package dan.example.dan_financial_book.common;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseHeader {

        private String code;
        private String message;
        private String origin;

        @Override
        public String toString() {
            String sb = "header : {" + "code : " + code + ", " +
                    "message : " + message + ", " +
                    "origin : " + origin + " }";
            return sb;
        }
}
