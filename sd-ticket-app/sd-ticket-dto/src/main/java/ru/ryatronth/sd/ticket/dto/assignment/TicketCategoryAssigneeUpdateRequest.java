package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(
    name = "TicketCategoryAssigneeUpdateRequest",
    description = """
        Обновление одного назначения.

        Правила:
        - MANAGER: categoryId=null
        - EXECUTOR: categoryId обязателен
        """
)
public record TicketCategoryAssigneeUpdateRequest(

    @Schema(description = "ID департамента", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID departmentId,

    @Schema(description = "ID пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID userId,

    @Schema(description = "ID категории (null для MANAGER)", nullable = true)
    UUID categoryId,

    @Schema(description = "Тип назначения", requiredMode = Schema.RequiredMode.REQUIRED)
    TicketAssignmentType assignmentType
) {}
