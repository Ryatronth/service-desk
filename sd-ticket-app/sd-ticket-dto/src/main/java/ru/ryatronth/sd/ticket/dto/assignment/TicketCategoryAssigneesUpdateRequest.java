package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Schema(description = "Запрос на назначение исполнителей категории в филиале (полная замена списка)")
public record TicketCategoryAssigneesUpdateRequest(

    @Schema(description = "ID филиала (department.id)", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull UUID departmentId,

    @Schema(description = "Список ID пользователей-исполнителей (полная замена)", requiredMode = Schema.RequiredMode.REQUIRED) @NotEmpty Set<UUID> userIds) {
}
