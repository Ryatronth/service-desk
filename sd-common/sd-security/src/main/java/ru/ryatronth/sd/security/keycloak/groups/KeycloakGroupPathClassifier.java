package ru.ryatronth.sd.security.keycloak.groups;

import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.ryatronth.sd.security.common.RoleNames;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.keycloak.groups.dto.GroupPathMatch;

@Slf4j
public class KeycloakGroupPathClassifier {

  @Getter
  private final String rolesRoot;

  @Getter
  private final String orgRoot;

  @Getter
  private final String branchesSeg;

  @Getter
  private final String workplacesSeg;

  public KeycloakGroupPathClassifier(SdKeycloakProperties props) {
    var cfg = props.getApi().getGroups();
    this.rolesRoot = GroupPathSplitter.normSegment(cfg.getRolesRoot());
    this.orgRoot = GroupPathSplitter.normSegment(cfg.getOrgRoot());
    this.branchesSeg = GroupPathSplitter.normSegment(cfg.getBranchesSegment());
    this.workplacesSeg = GroupPathSplitter.normSegment(cfg.getWorkplacesSegment());
  }

  /**
   * Классифицирует ОДИН group path:
   * - роль: /{rolesRoot}/{ROLE}
   * - оргструктура: /{orgRoot}/{branchesSeg}/{BRANCH}(/workplaces/{WP})?
   */
  public GroupPathMatch classify(String path) {
    if (path == null || path.isBlank() || !path.startsWith("/")) {
      return new GroupPathMatch(null, null, null);
    }

    List<String> p = GroupPathSplitter.split(path);
    if (p.isEmpty()) {
      return new GroupPathMatch(null, null, null);
    }

    if (p.size() >= 2 && GroupPathSplitter.eq(p.get(0), rolesRoot)) {
      String role = RoleNames.normalizeToSpringRole(p.getLast());
      return new GroupPathMatch(role, null, null);
    }

    if (p.size() >= 3 && GroupPathSplitter.eq(p.get(0), orgRoot) &&
        GroupPathSplitter.eq(p.get(1), branchesSeg)) {
      String branch = p.get(2);
      String workplace = null;

      if (p.size() >= 5 && GroupPathSplitter.eq(p.get(3), workplacesSeg)) {
        workplace = p.get(4);
      }

      return new GroupPathMatch(null, branch, workplace);
    }

    return new GroupPathMatch(null, null, null);
  }

}
