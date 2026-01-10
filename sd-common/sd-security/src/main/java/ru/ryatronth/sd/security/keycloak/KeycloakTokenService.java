package ru.ryatronth.sd.security.keycloak;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;

@RequiredArgsConstructor
public class KeycloakTokenService {

  private final RestClient keycloakRestClient;

  private final SdKeycloakProperties props;

  private volatile CachedToken cached;

  public synchronized String getAccessToken() {
    if (cached != null && cached.expiresAt().isAfter(Instant.now().plusSeconds(10))) {
      return cached.token();
    }

    String tokenUrl =
        props.getBaseUrl() + "/realms/" + props.getRealm() + "/protocol/openid-connect/token";

    var form = new LinkedMultiValueMap<String, String>();
    form.add("grant_type", "client_credentials");
    form.add("client_id", props.getApi().getClientId());
    form.add("client_secret", props.getApi().getClientSecret());

    TokenResponse tr = keycloakRestClient.post()
        .uri(tokenUrl)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(form)
        .retrieve()
        .body(TokenResponse.class);

    if (tr == null || tr.access_token() == null) {
      throw new IllegalStateException("Keycloak token response is empty");
    }

    Instant expiresAt = Instant.now().plusSeconds(tr.expires_in() == null ? 60 : tr.expires_in());
    cached = new CachedToken(tr.access_token(), expiresAt);
    return cached.token();
  }

  private record CachedToken(String token, Instant expiresAt) {
  }

  @SuppressWarnings("unused")
  private record TokenResponse(String access_token, Long expires_in, String token_type,
                               String scope) {
  }

}
