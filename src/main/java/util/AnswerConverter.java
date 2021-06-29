package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavliuk.spring.model.Answer;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import java.util.List;

public class AnswerConverter implements AttributeConverter<List<Answer>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(List<Answer> attribute) {
        return objectMapper.writeValueAsString(attribute);
    }

    @SneakyThrows
    @Override
    public List<Answer> convertToEntityAttribute(String dbData) {
        return objectMapper.readValue(dbData, new TypeReference<List<Answer>>(){});
    }
}
