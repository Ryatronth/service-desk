package ru.ryatronth.sd.security.keycloak.groups.dto;

import java.util.List;
import java.util.Map;

public record OrgDictionaryExtracted(List<String> branchCodes, Map<String, List<String>> workplacesByBranch) {}
