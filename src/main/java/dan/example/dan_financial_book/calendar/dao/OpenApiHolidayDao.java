package dan.example.dan_financial_book.calendar.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

// 공공 OpenApi 한국천문연구원_특일 정보 Dao
@Getter
@Setter
@ToString
@Builder
public class OpenApiHolidayDao {
    private Response response;

    @Getter
    @Setter
    private static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        private static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        private static class Body {
            private Items items;
            private Long numOfRows;
            private Long pageNo;
            private Long totalCount;

            @Getter
            @Setter
            private static class Items {
                private List<Item> items;

                @Getter
                @Setter
                private static class Item {
                    private String dateKind;
                    private String dateName;
                    private String isHoliday;
                    private Long locdate;
                    private Long seq;
                }
            }
        }
    }
}








