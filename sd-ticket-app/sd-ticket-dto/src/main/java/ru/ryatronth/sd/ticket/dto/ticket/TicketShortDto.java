package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TicketStatus;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.CategorySnapshotDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.UserSnapshotDto;

@Schema(name = "TicketListItemDto", description = "Элемент списка заявок")
public record TicketShortDto(

    @Schema(description = "UUID заявки")
    UUID id,

    @Schema(example = "SD-2026-000123")
    String number,

    @Schema(example = "Не работает доступ к VPN")
    String title,

    @Schema(description = "Статус")
    TicketStatus status,

    @Schema(description = "Приоритет (текущее значение)")
    TicketPriority priority,

    @Schema(description = "Снимок категории")
    CategorySnapshotDto categorySnapshot,

    @Schema(description = "Снимок отправителя")
    UserSnapshotDto requesterSnapshot,

    @Schema(description = "Снимок исполнителя")
    UserSnapshotDto assigneeSnapshot,

    @Schema(description = "Дедлайн (вычисленный)", example = "2026-01-10T12:00:00Z")
    Instant dueAt,

    @Schema(description = "Создано")
    Instant createdAt,

    @Schema(description = "Обновлено")
    Instant updatedAt
) {
}
