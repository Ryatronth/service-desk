package ru.ryatronth.sd.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.keycloak.KeycloakAdminService;
import ru.ryatronth.sd.security.keycloak.KeycloakAttributesReader;
import ru.ryatronth.sd.security.keycloak.KeycloakTokenService;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathClassifier;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakGroupPathsMapper;
import ru.ryatronth.sd.security.keycloak.groups.KeycloakOrgGroupsExtractor;

@Import(BaseSdSecurityConfig.class)
@AutoConfiguration
@ConditionalOnProperty(prefix = "sd.keycloak.api", name = "enabled", havingValue = "true")
public class SdSecurityKeycloakApiAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestClient keycloakRestClient(RestClient.Builder builder) {
        var httpClient = java.net.http.HttpClient.newBuilder().build();
        var requestFactory = new JdkClientHttpRequestFactory(httpClient);
        return builder.requestFactory(requestFactory).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakAttributesReader keycloakAttributesReader(SdKeycloakProperties properties) {
        return new KeycloakAttributesReader(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakGroupPathClassifier keycloakGroupPathClassifier(SdKeycloakProperties properties) {
        return new KeycloakGroupPathClassifier(properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakGroupPathsMapper keycloakGroupPathsMapper(KeycloakGroupPathClassifier classifier) {
        return new KeycloakGroupPathsMapper(classifier);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakOrgGroupsExtractor keycloakOrgGroupsExtractor(KeycloakGroupPathClassifier classifier) {
        return new KeycloakOrgGroupsExtractor(classifier);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakTokenService keycloakTokenService(RestClient keycloakRestClient, SdKeycloakProperties properties) {
        return new KeycloakTokenService(keycloakRestClient, properties);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeycloakAdminService keycloakAdminService(RestClient keycloakRestClient,
                                                     KeycloakTokenService tokenService,
                                                     SdKeycloakProperties properties) {
        return new KeycloakAdminService(keycloakRestClient, tokenService, properties);
    }

}
