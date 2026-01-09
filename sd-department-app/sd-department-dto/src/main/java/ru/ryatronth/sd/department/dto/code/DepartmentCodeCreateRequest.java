package ru.ryatronth.sd.department.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentCodeCreateRequest", description = "Запрос на создание кода подразделения")
public class DepartmentCodeCreateRequest {

    @Schema(description = "Код подразделения (уникальный)", example = "DEP-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

}
