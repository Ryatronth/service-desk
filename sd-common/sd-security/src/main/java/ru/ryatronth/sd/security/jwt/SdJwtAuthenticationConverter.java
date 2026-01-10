package ru.ryatronth.sd.security.jwt;

import java.util.LinkedHashSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import ru.ryatronth.sd.security.keycloak.groups.dto.SdUserGroups;

@Slf4j
@RequiredArgsConstructor
public class SdJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtClaimsExtractor claimsExtractor;

  /**
   * Стандартный конвертер прав из spring security (scope -> SCOPE_xxx).
   * Можно настроить prefix/claimName при необходимости, но обычно достаточно дефолта.
   */
  private final JwtGrantedAuthoritiesConverter defaultAuthoritiesConverter =
      new JwtGrantedAuthoritiesConverter();

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    var authorities = new LinkedHashSet<>(defaultAuthoritiesConverter.convert(jwt));

    for (String role : claimsExtractor.roles(jwt)) {
      authorities.add(new SimpleGrantedAuthority(role));
    }

    JwtAuthenticationToken token = new JwtAuthenticationToken(jwt, authorities);

    SdUserGroups groups = claimsExtractor.userGroups(jwt);
    token.setDetails(groups);

    log.debug("Пользователь аутентифицирован. Подразделение: {}, рабочее место: {}, роли: {}",
        groups.departmentCode(), groups.workplaceCode(), groups.roles());

    return token;
  }
}
