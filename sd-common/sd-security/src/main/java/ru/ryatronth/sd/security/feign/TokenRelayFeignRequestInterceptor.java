package ru.ryatronth.sd.security.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class TokenRelayFeignRequestInterceptor implements RequestInterceptor {

  @Override
  public void apply(RequestTemplate template) {
    Optional<String> tokenValue = currentBearerTokenValue();
    tokenValue.ifPresent(tv -> template.header("Authorization", "Bearer " + tv));
  }

  private Optional<String> currentBearerTokenValue() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth instanceof JwtAuthenticationToken jwtAuth) {
      return Optional.ofNullable(jwtAuth.getToken().getTokenValue());
    }
    return Optional.empty();
  }

}
