package ru.ryatronth.sd.iamsync.api.filter;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
public class IamUserFilters {

  @Parameter(description = "Поисковая строка (LIKE) по username/email/ФИО/phone/departmentId/workplaceId")
  private String q;

  @Parameter(description = "Фильтр по активности пользователя")
  private Boolean enabled;

  @Parameter(description = "Фильтр по username (строгое совпадение)")
  private String username;

  @Parameter(description = "Фильтр по email (строгое совпадение)")
  private String email;

  @Parameter(description = "Фильтр по телефону (строгое совпадение)")
  private String phone;

  @Parameter(description = "Фильтр по departmentId (строгое совпадение)")
  private String departmentId;

  @Parameter(description = "Фильтр по workplaceId (строгое совпадение)")
  private String workplaceId;

  @Parameter(description = "Режим объединения условий: AND (по умолчанию) или OR", example = "AND")
  private SearchMode mode = SearchMode.AND;

}

