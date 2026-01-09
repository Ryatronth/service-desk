package ru.ryatronth.sd.security.keycloak;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakGroupRepresentation;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakGroupTreeRepresentation;
import ru.ryatronth.sd.security.keycloak.dto.KeycloakUserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class KeycloakAdminService {

    private static final int CHILDREN_PAGE_SIZE = 200;

    private final RestClient keycloakRestClient;

    private final KeycloakTokenService tokenClient;

    private final SdKeycloakProperties sdKeycloakProperties;

    public List<KeycloakUserRepresentation> getUsersPage(int first, int max) {
        String url =
            sdKeycloakProperties.getBaseUrl() + "/admin/realms/" + sdKeycloakProperties.getRealm() + "/users?first=" + first
                + "&max=" + max;

        KeycloakUserRepresentation[] arr = keycloakRestClient.get()
                                                             .uri(url)
                                                             .accept(MediaType.APPLICATION_JSON)
                                                             .header(HttpHeaders.AUTHORIZATION,
                                                                     "Bearer " + tokenClient.getAccessToken())
                                                             .retrieve()
                                                             .body(KeycloakUserRepresentation[].class);

        return arr == null ? List.of() : List.of(arr);
    }

    public Set<String> getUserGroupPaths(String userKeycloakId) {
        String url = sdKeycloakProperties.getBaseUrl()
                         + "/admin/realms/" + sdKeycloakProperties.getRealm()
                         + "/users/" + userKeycloakId + "/groups";

        KeycloakGroupRepresentation[] arr = keycloakRestClient.get()
                                                              .uri(url)
                                                              .accept(MediaType.APPLICATION_JSON)
                                                              .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                                                              .retrieve()
                                                              .body(KeycloakGroupRepresentation[].class);

        if (arr == null) return Set.of();

        return Arrays.stream(arr)
                     .map(KeycloakGroupRepresentation::path)
                     .filter(p -> p != null && !p.isBlank())
                     .collect(Collectors.toUnmodifiableSet());
    }

    public Optional<KeycloakGroupTreeRepresentation> findGroupByName(String groupName) {
        String url = UriComponentsBuilder.fromUriString(sdKeycloakProperties.getBaseUrl())
                                         .path("/admin/realms/{realm}/groups")
                                         .queryParam("search", groupName)
                                         .buildAndExpand(sdKeycloakProperties.getRealm())
                                         .toUriString();

        KeycloakGroupTreeRepresentation[] arr = keycloakRestClient.get()
                                                                  .uri(url)
                                                                  .accept(MediaType.APPLICATION_JSON)
                                                                  .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                                                                  .retrieve()
                                                                  .body(KeycloakGroupTreeRepresentation[].class);

        if (arr == null) return Optional.empty();

        return Arrays.stream(arr)
                     .filter(g -> g != null && g.name() != null && g.name().equalsIgnoreCase(groupName))
                     .findFirst();
    }


    public KeycloakGroupTreeRepresentation getGroupTree(String groupId) {
        KeycloakGroupTreeRepresentation root = getGroupById(groupId);
        if (root == null) {
            throw new IllegalStateException("Группа Keycloak не найдена по id=" + groupId);
        }

        log.info("Строю дерево групп Keycloak от корня: id={}, path={}, subGroupCount={}",
                 root.id(), root.path(), root.subGroupCount());

        return fillChildrenRecursive(root);
    }

    private KeycloakGroupTreeRepresentation getGroupById(String groupId) {
        String url = sdKeycloakProperties.getBaseUrl()
                         + "/admin/realms/" + sdKeycloakProperties.getRealm()
                         + "/groups/" + groupId;

        return keycloakRestClient.get()
                                 .uri(url)
                                 .accept(MediaType.APPLICATION_JSON)
                                 .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                                 .retrieve()
                                 .body(KeycloakGroupTreeRepresentation.class);
    }

    private KeycloakGroupTreeRepresentation fillChildrenRecursive(KeycloakGroupTreeRepresentation group) {
        if (group == null || group.id() == null) {
            return group;
        }

        List<KeycloakGroupTreeRepresentation> children = getAllChildren(group.id());

        if (children.isEmpty()) {
            if (group.subGroupCount() != null && group.subGroupCount() > 0) {
                log.warn("Keycloak вернул subGroupCount={}, но /children пустой. id={}, path={}",
                         group.subGroupCount(), group.id(), group.path());
            }
            return new KeycloakGroupTreeRepresentation(
                group.id(), group.name(), group.path(), group.subGroupCount(), List.of()
            );
        }

        List<KeycloakGroupTreeRepresentation> filled = new ArrayList<>(children.size());
        for (KeycloakGroupTreeRepresentation child : children) {
            filled.add(fillChildrenRecursive(child));
        }

        return new KeycloakGroupTreeRepresentation(
            group.id(), group.name(), group.path(), group.subGroupCount(), List.copyOf(filled)
        );
    }

    private List<KeycloakGroupTreeRepresentation> getAllChildren(String groupId) {
        List<KeycloakGroupTreeRepresentation> all = new ArrayList<>();
        int first = 0;

        while (true) {
            List<KeycloakGroupTreeRepresentation> page = getChildrenPage(groupId, first, CHILDREN_PAGE_SIZE);
            if (page.isEmpty()) {
                break;
            }
            all.addAll(page);

            if (page.size() < CHILDREN_PAGE_SIZE) {
                break;
            }
            first += CHILDREN_PAGE_SIZE;
        }

        log.debug("Получено подгрупп для groupId={}: {}", groupId, all.size());
        return List.copyOf(all);
    }

    private List<KeycloakGroupTreeRepresentation> getChildrenPage(String groupId, int first, int max) {
        String url = sdKeycloakProperties.getBaseUrl()
                         + "/admin/realms/" + sdKeycloakProperties.getRealm()
                         + "/groups/" + groupId
                         + "/children?first=" + first
                         + "&max=" + max
                         + "&briefRepresentation=false";

        KeycloakGroupTreeRepresentation[] arr = keycloakRestClient.get()
                                                                  .uri(url)
                                                                  .accept(MediaType.APPLICATION_JSON)
                                                                  .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenClient.getAccessToken())
                                                                  .retrieve()
                                                                  .body(KeycloakGroupTreeRepresentation[].class);

        if (arr == null || arr.length == 0) {
            return List.of();
        }
        return List.of(arr);
    }

}
