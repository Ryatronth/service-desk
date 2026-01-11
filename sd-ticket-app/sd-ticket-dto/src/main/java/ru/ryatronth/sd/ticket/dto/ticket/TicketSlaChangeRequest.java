package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Schema(name = "TicketSlaChangeRequest", description = "Изменение SLA с причиной")
public record TicketSlaChangeRequest(

    @NotNull
    @Schema(description = "Новый дедлайн", requiredMode = Schema.RequiredMode.REQUIRED)
    Instant dueAt,

    @NotBlank
    @Schema(description = "Причина изменения", requiredMode = Schema.RequiredMode.REQUIRED, example = "Требуется выезд, срок увеличен")
    String reason
) {
}
