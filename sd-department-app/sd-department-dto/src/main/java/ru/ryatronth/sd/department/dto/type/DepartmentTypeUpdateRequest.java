package ru.ryatronth.sd.department.dto.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentTypeUpdateRequest", description = "Запрос на обновление типа подразделения")
public class DepartmentTypeUpdateRequest {

    @Schema(description = "Название типа (уникальное)", example = "Департамент", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

}
