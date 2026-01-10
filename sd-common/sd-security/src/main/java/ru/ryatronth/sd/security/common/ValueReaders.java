package ru.ryatronth.sd.security.common;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValueReaders {

  public static Optional<String> string(Map<String, ?> map, String key) {
    if (map == null || key == null || key.isBlank()) {
      return Optional.empty();
    }
    Object raw = map.get(key);
    if (raw == null) {
      return Optional.empty();
    }

    String v = (raw instanceof String s) ? s : String.valueOf(raw);
    v = v == null ? null : v.trim();
    if (v == null || v.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(v);
  }

  public static Optional<Boolean> bool(Map<String, ?> map, String key) {
    if (map == null || key == null || key.isBlank()) {
      return Optional.empty();
    }

    Object raw = map.get(key);
    switch (raw) {
      case null -> {
        return Optional.empty();
      }
      case Boolean b -> {
        return Optional.of(b);
      }
      case Number n -> {
        return Optional.of(n.intValue() != 0);
      }
      case String s -> {
        String v = s.trim();
        if (v.isEmpty()) {
          return Optional.empty();
        }
        boolean out =
            v.equalsIgnoreCase("true") || v.equalsIgnoreCase("1") || v.equalsIgnoreCase("yes") ||
                v.equalsIgnoreCase("y");
        return Optional.of(out);
      }
      default -> {
      }
    }

    log.debug("Не удалось привести значение к boolean: key={}, тип={}", key,
        raw.getClass().getName());
    return Optional.empty();
  }

  @SuppressWarnings("unchecked")
  public static Optional<Map<String, Object>> map(Map<String, ?> map, String key) {
    if (map == null || key == null || key.isBlank()) {
      return Optional.empty();
    }
    Object raw = map.get(key);
    if (raw instanceof Map<?, ?> m) {
      return Optional.of((Map<String, Object>) m);
    }
    return Optional.empty();
  }

  public static List<String> stringList(Map<String, ?> map, String key) {
    if (map == null || key == null || key.isBlank()) {
      return List.of();
    }
    Object raw = map.get(key);
    if (!(raw instanceof Collection<?> col)) {
      return List.of();
    }
    return col.stream().filter(Objects::nonNull).map(Object::toString).map(String::trim)
        .filter(s -> !s.isEmpty()).toList();
  }

  public static List<String> stringsFromListMap(Map<String, List<String>> map, String key) {
    if (map == null || key == null || key.isBlank()) {
      return List.of();
    }
    List<String> v = map.get(key);
    if (v == null) {
      return List.of();
    }
    return v.stream().filter(Objects::nonNull).map(String::trim).filter(s -> !s.isEmpty()).toList();
  }

  public static Optional<String> firstStringFromListMap(Map<String, List<String>> map, String key) {
    List<String> list = stringsFromListMap(map, key);
    return list.isEmpty() ? Optional.empty() : Optional.of(list.getFirst());
  }

}
