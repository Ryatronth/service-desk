package ru.ryatronth.sd.ticket.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.UserSnapshotDto;

@Schema(name = "TicketCommentDto", description = "Комментарий к заявке")
public record TicketCommentDto(

    @Schema(description = "UUID комментария")
    UUID id,

    @Schema(description = "Снимок автора комментария")
    UserSnapshotDto authorSnapshot,

    @Schema(example = "Проверил, нужен скрин ошибки.")
    String body,

    @Schema(description = "Вложения")
    List<TicketAttachmentDto> attachments,

    @Schema(description = "создано")
    Instant createdAt
) {
}
