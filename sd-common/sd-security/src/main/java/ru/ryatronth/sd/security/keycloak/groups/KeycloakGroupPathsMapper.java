package ru.ryatronth.sd.security.keycloak.groups;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.ryatronth.sd.security.keycloak.groups.dto.GroupPathMatch;
import ru.ryatronth.sd.security.keycloak.groups.dto.SdUserGroups;

import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class KeycloakGroupPathsMapper {

    private final KeycloakGroupPathClassifier classifier;

    public SdUserGroups map(Iterable<String> groupPaths, MultiValuePolicy policy) {
        Set<String> roles = new LinkedHashSet<>();
        String department = null;
        String workplace = null;

        if (groupPaths == null) {
            return new SdUserGroups(Set.of(), null, null);
        }

        for (String path : groupPaths) {
            GroupPathMatch m = classifier.classify(path);

            if (m.role() != null) {
                roles.add(m.role());
            }

            if (m.departmentCode() != null) {
                department = mergeSingle(department, m.departmentCode(), "подразделений", policy);
            }

            if (m.workplaceCode() != null) {
                workplace = mergeSingle(workplace, m.workplaceCode(), "рабочих мест", policy);
            }
        }

        return new SdUserGroups(Set.copyOf(roles), department, workplace);
    }

    private String mergeSingle(String current, String next, String label, MultiValuePolicy policy) {
        if (next == null || next.isBlank()) {
            return current;
        }
        if (current == null) {
            return next;
        }
        if (current.equals(next)) {
            return current;
        }

        if (policy == MultiValuePolicy.FIRST) {
            log.warn("Обнаружено несколько {} у пользователя: {} и {}. Беру первое значение: {}", label, current, next, current);
            return current;
        }

        throw new IllegalStateException("Обнаружено несколько " + label + " у пользователя: " + current + " и " + next);
    }

}
