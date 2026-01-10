package ru.ryatronth.sd.security.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sd.keycloak")
public class SdKeycloakProperties {

  private String baseUrl;

  private String realm;

  private Api api = new Api();

  @Getter
  @Setter
  public static class Api {

    private Boolean enabled = true;

    private String clientId;

    private String clientSecret;

    private Groups groups = new Groups();

    private Attributes attributes = new Attributes();

    @Getter
    @Setter
    public static class Groups {

      /**
       * Корневой сегмент для сервисных ролей в группах:
       * /service-desk/ROLE_ADMIN
       */
      private String rolesRoot = "service-desk";

      /**
       * Корневой сегмент для оргструктуры:
       * /department-codes/branches/DEP-001/workplaces/WP-1-123
       */
      private String orgRoot = "department-codes";

      /**
       * Сегмент ветки филиалов: /{orgRoot}/{branchesSegment}/{DEP}
       */
      private String branchesSegment = "branches";

      /**
       * Сегмент ветки рабочих мест: /.../{workplacesSegment}/{WP}
       */
      private String workplacesSegment = "workplaces";

    }

    @Getter
    @Setter
    public static class Attributes {

      private String patronymic = "patronymic";

      private String phone = "phone";

    }
  }
}
