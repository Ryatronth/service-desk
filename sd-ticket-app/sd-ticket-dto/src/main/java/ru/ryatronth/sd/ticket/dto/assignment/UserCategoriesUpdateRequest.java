package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Запрос на установку категорий, доступных пользователю как исполнителю (полная замена)")
public record UserCategoriesUpdateRequest(

    @Schema(description = "ID филиала (department.id)", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull UUID departmentId,

    @Schema(description = "Список ID категорий (полная замена)", requiredMode = Schema.RequiredMode.REQUIRED) @NotEmpty Set<UUID> categoryIds) {
}
