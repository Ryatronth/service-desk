package ru.ryatronth.sd.security.keycloak.groups;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakGroupTreeRepresentation;
import ru.ryatronth.sd.security.keycloak.groups.dto.GroupPathMatch;
import ru.ryatronth.sd.security.keycloak.groups.dto.OrgDictionaryExtracted;

@Slf4j
@RequiredArgsConstructor
public class KeycloakOrgGroupsExtractor {

  private final KeycloakGroupPathClassifier classifier;

  public OrgDictionaryExtracted extract(KeycloakGroupTreeRepresentation rootTree) {
    Map<String, Set<String>> workplacesByBranch = new LinkedHashMap<>();
    walk(rootTree, workplacesByBranch);

    List<String> branches = List.copyOf(workplacesByBranch.keySet());
    Map<String, List<String>> immutable = toImmutable(workplacesByBranch);

    int wpCount = workplacesByBranch.values().stream().mapToInt(Set::size).sum();
    log.info("Оргструктура извлечена из Keycloak. Подразделений: {}, рабочих мест: {}",
        branches.size(), wpCount);

    return new OrgDictionaryExtracted(branches, immutable);
  }

  private void walk(KeycloakGroupTreeRepresentation node,
                    Map<String, Set<String>> workplacesByBranch) {
    if (node == null || node.path() == null) {
      return;
    }

    GroupPathMatch m = classifier.classify(node.path());

    if (m.departmentCode() != null) {
      workplacesByBranch.computeIfAbsent(m.departmentCode(), __ -> new LinkedHashSet<>());
      if (m.workplaceCode() != null) {
        workplacesByBranch.get(m.departmentCode()).add(m.workplaceCode());
      }
    }

    if (node.subGroups() != null) {
      for (var child : node.subGroups()) {
        walk(child, workplacesByBranch);
      }
    }
  }

  private static Map<String, List<String>> toImmutable(Map<String, Set<String>> in) {
    Map<String, List<String>> out = new LinkedHashMap<>();
    for (var e : in.entrySet()) {
      out.put(e.getKey(), List.copyOf(e.getValue()));
    }
    return Map.copyOf(out);
  }

}
