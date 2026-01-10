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
    String departmentId,
    String workplaceId,
    Set<String> roles
) {
}
