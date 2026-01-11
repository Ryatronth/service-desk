package ru.ryatronth.sd.ticket.dto.attachment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(name = "TicketAttachmentCreateRequest", description = "Привязка файла (из file-service) к заявке/комментарию")
public record TicketAttachmentCreateRequest(

    @NotNull
    @Schema(description = "UUID файла в file-service", requiredMode = Schema.RequiredMode.REQUIRED)
    UUID fileId

) {
}
