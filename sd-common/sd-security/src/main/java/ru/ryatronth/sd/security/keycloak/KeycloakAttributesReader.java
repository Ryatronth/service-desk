package ru.ryatronth.sd.security.keycloak;

import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ryatronth.sd.security.common.ValueReaders;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;

@Slf4j
@RequiredArgsConstructor
public class KeycloakAttributesReader {

  private final SdKeycloakProperties props;

  public String patronymic(Map<String, List<String>> attrs) {
    String key = props.getApi().getAttributes().getPatronymic();
    return ValueReaders.firstStringFromListMap(attrs, key).orElse(null);
  }

  public String phone(Map<String, List<String>> attrs) {
    String key = props.getApi().getAttributes().getPhone();
    return ValueReaders.firstStringFromListMap(attrs, key).orElse(null);
  }

}
