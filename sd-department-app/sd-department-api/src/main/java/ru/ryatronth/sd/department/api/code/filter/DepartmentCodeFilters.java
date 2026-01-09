package ru.ryatronth.sd.department.api.code.filter;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springdoc.core.annotations.ParameterObject;
import ru.ryatronth.sd.department.api.common.SearchMode;

@Data
@ParameterObject
@Schema(name = "DepartmentCodeFilters", description = "Фильтры для поиска кодов подразделений")
public class DepartmentCodeFilters {

    @Parameter(description = "Поиск (LIKE) по code", example = "DEP")
    private String q;

    @Parameter(description = "фильтр по code", example = "DP-MSK-1")
    private String code;

    @Parameter(description = "Режим объединения условий: AND (по умолчанию) или OR", example = "AND")
    private SearchMode mode = SearchMode.AND;

}
