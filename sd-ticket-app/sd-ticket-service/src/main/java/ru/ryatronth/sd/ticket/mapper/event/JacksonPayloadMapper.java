package ru.ryatronth.sd.ticket.mapper.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JacksonPayloadMapper {

  private final ObjectMapper objectMapper;

  @Named("toJson")
  public String toJson(Object payload) {
    if (payload == null) {
      return null;
    }

    if (payload instanceof String s) {
      return s;
    }

    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize ticket event payload to JSON", e);
    }
  }

  @Named("fromJson")
  public Object fromJson(String json) {
    if (json == null || json.isBlank()) {
      return null;
    }

    try {
      return objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to deserialize ticket event payload from JSON", e);
    }
  }
}
