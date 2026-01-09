package ru.ryatronth.sd.department.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentCreateRequest", description = "Запрос на создание подразделения")
public class DepartmentCreateRequest {

    @Schema(description = "ID родительского подразделения (если есть)", example = "123e4567-e89b-12d3-a456-426614174000", nullable = true)
    private UUID parentId;

    @Schema(description = "Код подразделения (1:1, должен существовать в справочнике кодов)", example = "DEP-001", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID codeId;

    @Schema(description = "ID типа подразделения (если задан)", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID typeId;

    @Schema(description = "Название подразделения", example = "Отдел разработки", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Область/регион", example = "Москва", requiredMode = Schema.RequiredMode.REQUIRED)
    private String area;

    @Schema(description = "Адрес подразделения", example = "ул. Пушкина, д. 10", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

}
