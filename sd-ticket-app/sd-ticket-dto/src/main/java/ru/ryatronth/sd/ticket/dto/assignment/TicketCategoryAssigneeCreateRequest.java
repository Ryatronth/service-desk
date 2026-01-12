package ru.ryatronth.sd.ticket.dto.assignment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(
    name = "TicketCategoryAssigneeCreateRequest",
    description = """
        Создание одного назначения (для одного пользователя).

        Правила:
        - MANAGER: categoryId=null
        - EXECUTOR: categoryId обязателен
        """
)
public record TicketCategoryAssigneeCreateRequest(

    @Schema(description = "ID департамента", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID departmentId,

    @Schema(description = "ID пользователя", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID userId,

    @Schema(
        description = "ID категории. Должен быть null для MANAGER и обязателен для EXECUTOR.",
        nullable = true
    )
    UUID categoryId,

    @Schema(description = "Тип назначения", requiredMode = Schema.RequiredMode.REQUIRED)
    TicketAssignmentType assignmentType
) {}
