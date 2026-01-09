package ru.ryatronth.sd.security.keycloak.dto;

import java.util.List;

public record KeycloakGroupTreeRepresentation(
    String id,
    String name,
    String path,
    Integer subGroupCount,
    List<KeycloakGroupTreeRepresentation> subGroups
) {}
