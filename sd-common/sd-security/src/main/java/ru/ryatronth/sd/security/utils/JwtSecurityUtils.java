package ru.ryatronth.sd.security.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.security.jwt.JwtClaimsExtractor;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtSecurityUtils implements SecurityUtils {

    private final JwtClaimsExtractor extractor;

    @Override
    public Optional<Jwt> currentJwt() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken token) {
            return Optional.of(token.getToken());
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> currentUserId() {
        return currentJwt().map(extractor::userId).filter(v -> !v.isBlank());
    }

    @Override
    public Optional<CurrentUser> currentUser() {
        return currentJwt().map(jwt -> new CurrentUser(extractor.userId(jwt),
                                                       extractor.username(jwt),
                                                       extractor.email(jwt),
                                                       extractor.phone(jwt),
                                                       extractor.firstName(jwt),
                                                       extractor.lastName(jwt),
                                                       extractor.patronymic(jwt),
                                                       extractor.enabled(jwt),
                                                       extractor.departmentCode(jwt),
                                                       extractor.workplaceCode(jwt),
                                                       currentRoles()));
    }

    @Override
    public Set<String> currentRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return Collections.emptySet();
        }
        return auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toUnmodifiableSet());
    }

}
