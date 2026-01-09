package ru.ryatronth.sd.security.keycloak.groups.dto;

import java.util.Set;

public record SdUserGroups(Set<String> roles, String departmentCode, String workplaceCode) {}
