package ru.ryatronth.sd.iamsync.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ryatronth.sd.iamsync.domain.IamUserEntity;
import ru.ryatronth.sd.security.keycloak.KeycloakAttributesReader;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakUserRepresentation;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class KeycloakUserEntityMapper {

    @Autowired
    protected KeycloakAttributesReader reader;

    @Mapping(target = "id", expression = "java(toUuid(rep.id()))")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "enabled", expression = "java(rep.enabled() != null && rep.enabled())")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "departmentCode", ignore = true)
    @Mapping(target = "workplaceCode", ignore = true)
    @Mapping(target = "patronymic", ignore = true)
    public abstract void updateEntityFromKeycloak(KeycloakUserRepresentation rep, @MappingTarget IamUserEntity entity);

    @AfterMapping
    protected void fillAttributesAndGroups(KeycloakUserRepresentation rep, @MappingTarget IamUserEntity entity) {
        entity.setPatronymic(reader.patronymic(rep.attributes()));
        entity.setPhone(reader.phone(rep.attributes()));
    }

    public UUID toUuid(String keycloakId) {
        if (keycloakId == null || keycloakId.isBlank()) {
            return UUID.randomUUID();
        }
        try {
            return UUID.fromString(keycloakId);
        } catch (Exception ignored) {
            return UUID.nameUUIDFromBytes(keycloakId.getBytes(StandardCharsets.UTF_8));
        }
    }

}
