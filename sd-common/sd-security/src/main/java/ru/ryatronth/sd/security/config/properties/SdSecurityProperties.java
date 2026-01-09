package ru.ryatronth.sd.security.config.properties;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "sd.security")
public class SdSecurityProperties {

    private boolean enabled = true;

    private Http http = new Http();

    private Claims claims = new Claims();

    private Swagger swagger = new Swagger();

    @Setter
    @Getter
    public static class Http {

        private boolean csrfEnabled = false;

        private List<String> permitAll = List.of("/actuator/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html");

        private List<String> corsAllowedOrigins = List.of("http://localhost:5173", "http://127.0.0.1:5173");
    }

    @Setter
    @Getter
    public static class Claims {

        private String userId = "sub";

        private String username = "preferred_username";

        private String email = "email";

        private String phone = "phone";

        private String firstName = "given_name";

        private String lastName = "family_name";

        private String patronymic = "patronymic";

        private String enabled = "enabled";

        private String groups = "groups";

    }

    @Setter
    @Getter
    public static class Swagger {

        private boolean enabled = true;

        private String bearerSchemeName = "bearerAuth";

        private String oauth2SchemeName = "oauth2";

        private String tokenUrl;

    }
}
