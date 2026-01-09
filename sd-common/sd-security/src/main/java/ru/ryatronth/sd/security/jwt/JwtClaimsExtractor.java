package ru.ryatronth.sd.security.jwt;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.ryatronth.sd.security.common.ValueReaders;
import ru.ryatronth.sd.security.config.properties.SdSecurityProperties;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathsMapper;
import ru.ryatronth.sd.security.keycloak.groups.MultiValuePolicy;
import ru.ryatronth.sd.security.keycloak.groups.dto.SdUserGroups;

@Slf4j
@RequiredArgsConstructor
public class JwtClaimsExtractor {

    private final SdSecurityProperties properties;

    private final KeycloakGroupPathsMapper groupPathsMapper;

    public String userId(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getUserId()).orElse(null);
    }

    public String username(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getUsername()).orElse(null);
    }

    public String email(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getEmail()).orElse(null);
    }

    public String phone(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getPhone()).orElse(null);
    }

    public String firstName(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getFirstName()).orElse(null);
    }

    public String lastName(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getLastName()).orElse(null);
    }

    public String patronymic(Jwt jwt) {
        return claimAsString(jwt, properties.getClaims().getPatronymic()).orElse(null);
    }

    public boolean enabled(Jwt jwt) {
        return claimAsBoolean(jwt, properties.getClaims().getEnabled()).orElse(true);
    }

    public SdUserGroups userGroups(Jwt jwt) {
        if (jwt == null) {
            return new SdUserGroups(Set.of(), null, null);
        }

        List<String> groups;
        try {
            groups = jwt.getClaimAsStringList(properties.getClaims().getGroups());
        } catch (Exception e) {
            log.warn("Не удалось прочитать claim '{}' из JWT. Группы будут пустыми. Причина: {}", properties.getClaims().getGroups(), e.getMessage());
            groups = List.of();
        }

        try {
            return groupPathsMapper.map(groups, MultiValuePolicy.FIRST);
        } catch (Exception e) {
            log.warn("Не удалось сопоставить группы из JWT. Подразделение/рабочее место/роли будут пустыми. Причина: {}",
                     e.getMessage());
            return new SdUserGroups(Set.of(), null, null);
        }
    }

    public String departmentCode(Jwt jwt) {
        return userGroups(jwt).departmentCode();
    }

    public String workplaceCode(Jwt jwt) {
        return userGroups(jwt).workplaceCode();
    }

    public Set<String> roles(Jwt jwt) {
        return userGroups(jwt).roles();
    }

    private Optional<String> claimAsString(Jwt jwt, String key) {
        if (jwt == null) {
            return Optional.empty();
        }
        return ValueReaders.string(jwt.getClaims(), key);
    }

    private Optional<Boolean> claimAsBoolean(Jwt jwt, String key) {
        if (jwt == null) {
            return Optional.empty();
        }
        return ValueReaders.bool(jwt.getClaims(), key);
    }

}
