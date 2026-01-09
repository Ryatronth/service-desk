package ru.ryatronth.sd.department.dto.department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryatronth.sd.department.dto.code.DepartmentCodeDto;
import ru.ryatronth.sd.department.dto.type.DepartmentTypeDto;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "DepartmentResponse", description = "Полная модель подразделения (с раскрытыми связями)")
public class DepartmentDto {

    @Schema(description = "ID подразделения", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Родительское подразделение (кратко)", nullable = true)
    private DepartmentParentDto parent;

    @Schema(description = "Код подразделения (1:1)", requiredMode = Schema.RequiredMode.REQUIRED)
    private DepartmentCodeDto code;

    @Schema(description = "Тип подразделения", nullable = true)
    private DepartmentTypeDto type;

    @Schema(description = "Название подразделения", example = "Отдел разработки")
    private String name;

    @Schema(description = "Область/регион", example = "Москва")
    private String area;

    @Schema(description = "Адрес подразделения", example = "ул. Пушкина, д. 10")
    private String address;

    @Schema(description = "Дата создания", example = "2026-01-08T13:41:31Z")
    private Instant createdAt;

    @Schema(description = "Дата изменения", example = "2026-01-08T13:41:31Z")
    private Instant updatedAt;

}
