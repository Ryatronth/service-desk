package ru.ryatronth.sd.security.dto;

import java.util.Set;

public record CurrentUser(
    String userId,
    String username,
    String email,
    String phone,
    String firstName,
    String lastName,
    String patronymic,
    boolean enabled,
    String departmentCode,
    String workplaceCode,
    Set<String> roles
) {

  public String buildFullName() {
    String last = lastName() == null ? "" : lastName().trim();
    String first = firstName() == null ? "" : firstName().trim();
    String pat = patronymic() == null ? "" : patronymic().trim();

    String full = (last + " " + first + " " + pat).trim().replaceAll("\\s+", " ");
    return full.isBlank() ? username() : full;
  }

}
