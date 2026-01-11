package ru.ryatronth.sd.ticket.dto.event;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.event.snapshot.TicketSnapshotDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.UserSnapshotDto;

@Schema(name = "TicketEventDto", description = "Событие в истории заявки")
public record TicketEventDto(
    @Schema(description = "UUID события истории")
    UUID id,

    @Schema(description = "Снимок обращения в момент выполнения события")
    TicketSnapshotDto ticketSnapshot,

    @Schema(description = "Тип события")
    TicketEventType type,

    @Schema(description = "Снимок автора события")
    UserSnapshotDto actorSnapshot,

    @Schema(description = "JSON payload события", example = "{\"from\":\"NEW\",\"to\":\"IN_PROGRESS\"}")
    String payload,

    @Schema(description = "Короткий комментарий (опционально)", example = "Назначено на инженера")
    String comment,

    @Schema(description = "Создано")
    Instant createdAt
) {
}
