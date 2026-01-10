package ru.ryatronth.sd.department.service.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeEntity;
import ru.ryatronth.sd.department.domain.code.DepartmentCodeRepository;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.keycloak.KeycloakAdminService;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakOrgGroupsExtractor;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrgDictionarySyncService {

  private final SdKeycloakProperties props;

  private final KeycloakAdminService admin;

  private final KeycloakOrgGroupsExtractor extractor;

  private final DepartmentCodeRepository departmentCodeRepository;

  @Transactional
  public SyncResult sync() {
    String orgRoot = props.getApi().getGroups().getOrgRoot();

    log.info("Запуск синхронизации оргсправочника из Keycloak. Корневая группа: {}", orgRoot);

    var rootOpt = admin.findGroupByName(orgRoot);
    if (rootOpt.isEmpty()) {
      log.warn("Корневая группа оргструктуры не найдена в Keycloak: {}", orgRoot);
      return new SyncResult(0, 0);
    }

    var tree = admin.getGroupTree(rootOpt.get().id());
    var extracted = extractor.extract(tree);

    int branchUpserts = 0;
    int workplaceUpserts = 0;

    for (String branchCode : extracted.branchCodes()) {
      upsertDepartmentCode(branchCode);
      branchUpserts++;
    }

    log.info("Синхронизация оргсправочника завершена. Подразделений обновлено/создано: {}",
        branchUpserts);
    return new SyncResult(branchUpserts, workplaceUpserts);
  }

  private DepartmentCodeEntity upsertDepartmentCode(String code) {
    String normalized = norm(code);

    return departmentCodeRepository.findByCodeIgnoreCase(normalized).map(existing -> {
      existing.setCode(normalized);
      return departmentCodeRepository.save(existing);
    }).orElseGet(() -> departmentCodeRepository.save(
        DepartmentCodeEntity.builder().code(normalized).build()));
  }

  private static String norm(String s) {
    return s == null ? null : s.trim();
  }

  public record SyncResult(int branchesUpserted, int workplacesUpserted) {
  }

}
