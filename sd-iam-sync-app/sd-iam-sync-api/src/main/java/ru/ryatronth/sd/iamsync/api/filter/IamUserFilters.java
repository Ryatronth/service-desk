package ru.ryatronth.sd.iamsync.api.filter;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;

@Data
@ParameterObject
public class IamUserFilters {

  @Parameter(description = "Поисковая строка (LIKE) по username/email/ФИО/phone/departmentCode/workplaceCode")
  private String q;

  @Parameter(description = "Фильтр по активности пользователя")
  private Boolean enabled;

  @Parameter(description = "Фильтр по username (строгое совпадение)")
  private String username;

  @Parameter(description = "Фильтр по email (строгое совпадение)")
  private String email;

  @Parameter(description = "Фильтр по телефону (строгое совпадение)")
  private String phone;

  @Parameter(description = "Фильтр по departmentCode (строгое совпадение)")
  private String departmentCode;

  @Parameter(description = "Фильтр по workplaceCode (строгое совпадение)")
  private String workplaceCode;

  @Parameter(description = "Режим объединения условий: AND (по умолчанию) или OR", example = "AND")
  private SearchMode mode = SearchMode.AND;

}

