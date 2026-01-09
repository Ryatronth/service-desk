package ru.ryatronth.sd.department.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentCodeDto", description = "Код подразделения (1:1)")
public class DepartmentCodeDto {

    @Schema(description = "ID кода подразделения (1:1). Ссылка на справочник department_code", example = "123e4567-e89b-12d3-a456-426614174000", requiredMode = Schema.RequiredMode.REQUIRED)
    private UUID codeId;

    @Schema(description = "Код подразделения", example = "DEP-001")
    private String code;

    @Schema(description = "Дата создания", example = "2026-01-08T13:41:31Z")
    private Instant createdAt;

    @Schema(description = "Дата изменения", example = "2026-01-08T13:41:31Z")
    private Instant updatedAt;

}
