package ru.ryatronth.sd.department.api.department.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import ru.ryatronth.sd.department.api.common.SearchMode;

@Data
@ParameterObject
@Schema(name = "DepartmentFilters", description = "Фильтры для поиска подразделений")
public class DepartmentParentFilters {

  @Parameter(description = "Поиск (LIKE) по name/area/address/code/typeName", example = "москва")
  private String q;

  @Parameter(description = "Фильтр по id текущего филиала (Если филиал уже создан)", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID currentId;

  @Parameter(description = "Фильтр по codeId (department_code.id)", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID codeId;

  @Parameter(description = "Фильтр по typeId (department_type.id)", example = "123e4567-e89b-12d3-a456-426614174000")
  private UUID typeId;

  @Parameter(description = "Режим объединения условий: AND (по умолчанию) или OR", example = "AND")
  private SearchMode mode = SearchMode.AND;

}
