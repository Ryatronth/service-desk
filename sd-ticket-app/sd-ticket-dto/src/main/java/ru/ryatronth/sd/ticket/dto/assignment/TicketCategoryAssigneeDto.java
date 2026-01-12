package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryDto;

@Schema(name = "TicketCategoryAssigneeDto", description = "Назначение пользователя (исполнитель/менеджер)")
public record TicketCategoryAssigneeDto(

    @Schema(description = "ID назначения", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID id,

    @Schema(description = "ID департамента", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID departmentId,

    @Schema(
        description = "Категория. null означает назначение на все категории (MANAGER).",
        nullable = true
    )
    TicketCategoryDto category,

    @Schema(description = "ID пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID userId,

    @Schema(description = "Тип назначения", requiredMode = Schema.RequiredMode.REQUIRED)
    TicketAssignmentType assignmentType,

    @Schema(description = "Дата создания", requiredMode = Schema.RequiredMode.REQUIRED)
    Instant createdAt
) {}
