package ru.ryatronth.sd.ticket.dto.attachment;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.attachment.shapshot.FileSnapshotDto;

@Schema(name = "TicketAttachmentDto", description = "Вложение (файл) в заявке/комментарии")
public record TicketAttachmentDto(
    @Schema(description = "UUID вложения")
    UUID id,

    @Schema(description = "UUID обращения")
    UUID ticketId,

    @Schema(description = "UUID комментария (если вложение относится к комментарию)")
    UUID commentId,

    @Schema(description = "Снимок метаданных файла")
    FileSnapshotDto fileSnapshot,

    @Schema(description = "Создано")
    Instant createdAt
) {
}
