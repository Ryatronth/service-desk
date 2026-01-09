package ru.ryatronth.sd.department.api.type.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import ru.ryatronth.sd.department.api.common.SearchMode;

@Data
@ParameterObject
@Schema(name = "DepartmentTypeFilters", description = "Фильтры для поиска типов подразделений")
public class DepartmentTypeFilters {

    @Parameter(description = "Поиск (LIKE) по name", example = "офис")
    private String q;

    @Parameter(description = "Режим объединения условий: AND (по умолчанию) или OR", example = "AND")
    private SearchMode mode = SearchMode.AND;

}
