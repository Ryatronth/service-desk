package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(name = "TicketAssigneeChangeRequest", description = "Запрос на замену ответственного (исполнителя) обращения")
public record TicketAssigneeChangeRequest(

    @Schema(description = "ID нового ответственного", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID assigneeUserId,

    @Schema(description = "Комментарий/причина смены ответственного")
    String comment
) {
}
