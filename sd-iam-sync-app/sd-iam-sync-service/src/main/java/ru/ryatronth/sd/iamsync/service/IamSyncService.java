package ru.ryatronth.sd.iamsync.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.iamsync.domain.IamUserEntity;
import ru.ryatronth.sd.iamsync.domain.IamUserRepository;
import ru.ryatronth.sd.iamsync.mapper.KeycloakUserEntityMapper;
import ru.ryatronth.sd.security.keycloak.KeycloakAdminService;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakUserRepresentation;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathsMapper;
import ru.ryatronth.sd.security.keycloak.groups.MultiValuePolicy;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IamSyncService {

    private static final int PAGE_SIZE = 200;

    private final KeycloakAdminService adminClient;

    private final IamUserRepository userRepository;

    private final KeycloakUserEntityMapper keycloakMapper;

    private final KeycloakGroupPathsMapper groupPathsMapper;

    @Transactional
    public SyncResult syncAllUsers() {
        int first = 0;
        long processed = 0;

        log.info("Запуск синхронизации пользователей из Keycloak...");

        while (true) {
            List<KeycloakUserRepresentation> users = adminClient.getUsersPage(first, PAGE_SIZE);
            if (users.isEmpty()) {
                break;
            }

            for (var u : users) {
                upsertOne(u);
                processed++;
            }

            if (users.size() < PAGE_SIZE) {
                break;
            }
            first += PAGE_SIZE;
        }

        log.info("Синхронизация пользователей завершена. Обработано: {}", processed);
        return new SyncResult(processed);
    }

    private void upsertOne(KeycloakUserRepresentation rep) {
        UUID id = keycloakMapper.toUuid(rep.id());

        IamUserEntity entity = userRepository.findById(id).orElseGet(() -> {
            IamUserEntity e = new IamUserEntity();
            e.setId(id);
            return e;
        });

        keycloakMapper.updateEntityFromKeycloak(rep, entity);

        var groupPaths = adminClient.getUserGroupPaths(rep.id());
        var mapped = groupPathsMapper.map(groupPaths, MultiValuePolicy.FAIL);

        entity.getRoles().clear();
        entity.getRoles().addAll(mapped.roles());
        entity.setDepartmentCode(mapped.departmentCode());
        entity.setWorkplaceCode(mapped.workplaceCode());

        userRepository.save(entity);

        log.debug("Пользователь синхронизирован: id={}, username={}, департамент={}, рабочее место={}, роли={}",
                  rep.id(),
                  rep.username(),
                  mapped.departmentCode(),
                  mapped.workplaceCode(),
                  mapped.roles());
    }

    public record SyncResult(long processedUsers) {}

}
