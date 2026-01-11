package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import ru.ryatronth.sd.ticket.dto.TicketStatus;

@Schema(name = "TicketStatusChangeRequest", description = "Смена статуса заявки")
public record TicketStatusChangeRequest(

    @NotNull
    @Schema(description = "Новый статус", requiredMode = Schema.RequiredMode.REQUIRED)
    TicketStatus status,

    @Schema(description = "Комментарий (опционально)", example = "Нужна дополнительная информация от пользователя")
    String comment
) {
}
