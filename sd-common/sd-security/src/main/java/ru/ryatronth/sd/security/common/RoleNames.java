package ru.ryatronth.sd.security.common;

import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleNames {

  public static String normalizeToSpringRole(String role) {
    if (role == null) {
      return null;
    }

    String v =
        role.trim().replace('-', '_').replace('.', '_').replace(' ', '_').toUpperCase(Locale.ROOT);

    if (v.isEmpty()) {
      return null;
    }

    return v.startsWith("ROLE_") ? v : "ROLE_" + v;
  }

}
