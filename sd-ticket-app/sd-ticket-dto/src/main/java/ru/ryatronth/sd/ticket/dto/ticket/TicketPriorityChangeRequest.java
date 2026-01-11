package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@Schema(name = "TicketPriorityChangeRequest", description = "Изменение приоритета с причиной")
public record TicketPriorityChangeRequest(

    @NotNull
    @Schema(description = "Новый приоритет", requiredMode = Schema.RequiredMode.REQUIRED)
    TicketPriority priority,

    @NotBlank
    @Schema(description = "Причина изменения", requiredMode = Schema.RequiredMode.REQUIRED, example = "Критичная поломка у руководителя")
    String reason
) {
}
