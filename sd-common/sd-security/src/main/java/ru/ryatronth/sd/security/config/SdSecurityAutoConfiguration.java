package ru.ryatronth.sd.security.config;

import feign.RequestInterceptor;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.resource.servlet.OAuth2ResourceServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.config.properties.SdSecurityProperties;
import ru.ryatronth.sd.security.feign.TokenRelayFeignRequestInterceptor;
import ru.ryatronth.sd.security.jwt.JwtClaimsExtractor;
import ru.ryatronth.sd.security.jwt.SdJwtAuthenticationConverter;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathClassifier;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathsMapper;
import ru.ryatronth.sd.security.utils.JwtSecurityUtils;
import ru.ryatronth.sd.security.utils.RandomSecurityUtils;
import ru.ryatronth.sd.security.utils.SecurityUtils;
import ru.ryatronth.sd.security.utils.SystemUserProvider;

@Slf4j
@Import(BaseSdSecurityConfig.class)
@AutoConfiguration(before = {OAuth2ResourceServerAutoConfiguration.class})
public class SdSecurityAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public UrlBasedCorsConfigurationSource corsConfigurationSource(SdSecurityProperties properties) {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(properties.getHttp().getCorsAllowedOrigins());
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    config.setExposedHeaders(
        List.of("Authorization", "Link", "X-Total-Count", "Content-Disposition"));
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  @ConditionalOnMissingBean
  public KeycloakGroupPathClassifier keycloakGroupPathClassifier(
      SdKeycloakProperties keycloakProperties) {
    return new KeycloakGroupPathClassifier(keycloakProperties);
  }

  @Bean
  @ConditionalOnMissingBean
  public KeycloakGroupPathsMapper keycloakGroupPathsMapper(KeycloakGroupPathClassifier classifier) {
    return new KeycloakGroupPathsMapper(classifier);
  }

  @Bean
  @ConditionalOnMissingBean
  public JwtClaimsExtractor jwtClaimsExtractor(SdSecurityProperties securityProperties,
                                               KeycloakGroupPathsMapper groupPathsMapper) {
    return new JwtClaimsExtractor(securityProperties, groupPathsMapper);
  }

  @Bean
  @ConditionalOnMissingBean(name = "jwtAuthenticationConverter")
  public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter(
      JwtClaimsExtractor claimsExtractor) {
    return new SdJwtAuthenticationConverter(claimsExtractor);
  }


  @Bean
  @ConditionalOnClass(RequestInterceptor.class)
  @ConditionalOnMissingBean
  public RequestInterceptor tokenRelayFeignRequestInterceptor() {
    return new TokenRelayFeignRequestInterceptor();
  }

  @Bean
  @ConditionalOnMissingBean(SecurityUtils.class)
  public SecurityUtils securityUtils(SdSecurityProperties properties,
                                     JwtClaimsExtractor jwtClaimsExtractor) {
    return properties.isEnabled() ? new JwtSecurityUtils(jwtClaimsExtractor) :
        new RandomSecurityUtils();
  }

  @Bean
  @ConditionalOnMissingBean
  public SystemUserProvider systemUserProvider() {
    return new SystemUserProvider();
  }

  @Bean
  @ConditionalOnMissingBean(SecurityFilterChain.class)
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 SdSecurityProperties properties,
                                                 Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter)
      throws Exception {

    http.cors(Customizer.withDefaults());

    if (!properties.isEnabled()) {
      http.authorizeHttpRequests(a -> a.anyRequest().permitAll())
          .csrf(AbstractHttpConfigurer::disable)
          .cors(AbstractHttpConfigurer::disable)
          .securityContext(AbstractHttpConfigurer::disable)
          .sessionManagement(AbstractHttpConfigurer::disable);
      return http.build();
    }

    if (!properties.getHttp().isCsrfEnabled()) {
      http.csrf(AbstractHttpConfigurer::disable);
    }

    var permit = properties.getHttp().getPermitAll().toArray(String[]::new);

    http.authorizeHttpRequests(
            auth -> auth.requestMatchers(permit).permitAll().anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(
            jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));

    return http.build();
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnClass(OpenAPI.class)
  public OpenAPI sdOpenApiSecurity(SdSecurityProperties properties) {
    var swagger = properties.getSwagger();

    String bearerName = swagger.getBearerSchemeName();
    String oauth2Name = swagger.getOauth2SchemeName();

    var components = new Components();

    components.addSecuritySchemes(bearerName,
        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"));

    String tokenUrl = swagger.getTokenUrl();
    if (tokenUrl != null && !tokenUrl.isBlank()) {
      components.addSecuritySchemes(oauth2Name,
          new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
              .flows(new OAuthFlows().clientCredentials(new OAuthFlow().tokenUrl(
                  tokenUrl).scopes(new Scopes()))));
    }

    var api = new OpenAPI().components(components);
    api.addSecurityItem(new SecurityRequirement().addList(bearerName));
    if (tokenUrl != null && !tokenUrl.isBlank()) {
      api.addSecurityItem(new SecurityRequirement().addList(oauth2Name));
    }
    return api;
  }

}
