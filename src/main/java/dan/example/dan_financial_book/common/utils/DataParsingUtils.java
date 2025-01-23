package dan.example.dan_financial_book.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.stream.Collectors;

public class DataParsingUtils {
    // JSON 객체를 문자열로 변환하고 출력하는 메소드
    public static String convertObjectToJsonString(Object object) {
        // Jackson ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 출력 시 가독성을 높이기 위해 읽기 쉬운 형식으로 설정
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 객체를 JSON 문자열로 변환
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    // 제네릭 타입 T를 사용하여 어떤 클래스의 리스트도 문자열로 변환 가능
    public static <T> String convertListToString(List<T> list) {
        if (list == null || list.isEmpty()) {
            return "[]";  // 빈 리스트 처리를 위한 반환값
        }

        // 리스트의 각 객체에 대해 toString() 호출 후 합침
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
