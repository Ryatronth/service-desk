package ru.ryatronth.sd.security.keycloak.dto;

import java.util.List;
import java.util.Map;

public record KeycloakUserRepresentation(
    String id,
    String username,
    String email,
    String firstName,
    String lastName,
    Boolean enabled,
    Map<String, List<String>> attributes
) {
}
