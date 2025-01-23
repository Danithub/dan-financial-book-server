package dan.example.dan_financial_book.calendar.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CustomListDeserializer extends JsonDeserializer<List> {

    @Override
    public List deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        // 노드가 text일 경우 빈 문자열인지 검사
        if (node.isTextual() && node.asText().isEmpty() || node.isNull()) {
            return Collections.emptyList(); // 빈 문자열이면 빈 리스트 반환
        }

        // 그렇지 않다면 일반적인 리스트로 처리
        return jp.getCodec().treeToValue(node, List.class);
    }
}