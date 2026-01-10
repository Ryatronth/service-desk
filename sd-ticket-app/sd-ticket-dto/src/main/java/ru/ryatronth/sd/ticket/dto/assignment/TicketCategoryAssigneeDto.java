package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "Связь категории обращения и исполнителя в рамках филиала (department.id)")
public record TicketCategoryAssigneeDto(

    @Schema(description = "ID связи", example = "a3f9c2f2-6d8b-4f01-9b3e-9d4e0c9a2f31") UUID id,

    @Schema(description = "ID категории обращения", example = "3ad1f01c-1c36-4b15-9a5e-1d4d67d5f4a2") UUID categoryId,

    @Schema(description = "ID филиала (department.id)", example = "b7a8bd68-7dcb-4c8b-ae73-3c1f5e6f8c21") UUID departmentId,

    @Schema(description = "ID пользователя-исполнителя", example = "d3d6b9ef-7f6c-4f6f-9fcb-0e4c5dd63aa1") UUID userId) {
}
