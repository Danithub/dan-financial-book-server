package dan.example.dan_financial_book.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DataUtils {
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

    // 안전하게 Object를 Map<String, Object>로 캐스팅
    @SuppressWarnings("unchecked")
    public static HashMap<String, Object> safeCastToMap(Object obj) {
        if (obj instanceof HashMap) {
            // 키가 String인지, 값이 Object인지 확인
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) obj).entrySet()) {
                if (!(entry.getKey() instanceof String)) {
                    log.info("DataUtils safeCastToMap Error Not A String =============> {}", entry.getKey());
                    throw new ClassCastException("키가 String이 아닙니다: " + entry.getKey());
                }
                // 값이 Object인지 확인할 필요 없음 (모든 것이 Object)
            }
            return (HashMap<String, Object>) obj; // 안전한 캐스팅
        } else {
            log.info("DataUtils safeCastToMap Error Not A Map =============> {}", "객체가 Map이 아닙니다.");
            throw new ClassCastException("객체가 Map이 아닙니다.");
        }
    }

    // 안전하게 Object를 ArrayList<Object>로 캐스팅
    @SuppressWarnings("unchecked")
    public static ArrayList<HashMap<String, Object>> safeCastToArrayList(Object obj) {
        if (obj instanceof ArrayList) {
            return (ArrayList<HashMap<String, Object>>) new ArrayList<>((ArrayList<?>) obj);
        } else {
            throw new ClassCastException("객체가 ArrayList가 아닙니다.");
        }
    }
}
