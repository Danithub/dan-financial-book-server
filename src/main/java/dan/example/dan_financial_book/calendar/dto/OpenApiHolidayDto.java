package dan.example.dan_financial_book.calendar.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

// 공공 OpenApi 한국천문연구원_특일 정보 Dto
@Getter
@Setter
public class OpenApiHolidayDto {
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private Header header;
        private Body body;

        @Getter
        @Setter
        public static class Header {
            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @Setter
        public static class Body {
            private Items items;
            private Long numOfRows;
            private Long pageNo;
            private Long totalCount;

            @Getter
            @Setter
            public static class Items {
                private List<Item> item;

                @Getter
                @Setter
                public static class Item {
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

