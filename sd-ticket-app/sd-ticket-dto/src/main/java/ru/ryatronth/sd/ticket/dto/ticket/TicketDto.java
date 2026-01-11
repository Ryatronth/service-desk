package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TicketStatus;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.CategorySnapshotDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.DepartmentSnapshotDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.UserSnapshotDto;
import ru.ryatronth.sd.ticket.dto.ticket.snapshot.WorkplaceSnapshotDto;

@Schema(name = "TicketDto", description = "Заявка (текущее состояние)")
public record TicketDto(

    @Schema(description = "UUID заявки")
    UUID id,

    @Schema(description = "Номер заявки", example = "SD-2026-000123")
    String number,

    @Schema(description = "Заголовок", example = "Не работает доступ к VPN")
    String title,

    @Schema(description = "Описание", example = "При попытке подключиться ошибка 691...")
    String description,

    @Schema(description = "Снимок категории")
    CategorySnapshotDto categorySnapshot,

    @Schema(description = "Приоритет (текущее значение)")
    TicketPriority priority,

    @Schema(description = "Дедлайн (вычисленный)", example = "2026-01-10T12:00:00Z")
    Instant dueAt,

    @Schema(description = "Статус")
    TicketStatus status,

    @Schema(description = "Снимок отправителя")
    UserSnapshotDto requesterSnapshot,

    @Schema(description = "Снимок исполнителя")
    UserSnapshotDto assigneeSnapshot,

    @Schema(description = "Снимок филиала отправителя")
    DepartmentSnapshotDto requesterDepartmentSnapshot,

    @Schema(description = "Снимок филиала, к которому в данный момент назначена заявка")
    DepartmentSnapshotDto currentDepartmentSnapshot,

    @Schema(description = "Снимок рабочего места отправителя")
    WorkplaceSnapshotDto requesterWorkplaceSnapshot,

    @Schema(description = "Дата закрытия")
    Instant closedAt,

    @Schema(description = "Затраченное время (минуты)", example = "90")
    Long spentTimeMinutes,

    @Schema(description = "Затраченные средства (в центах/копейках)", example = "150000")
    BigDecimal spentMoney,

    @Schema(description = "Создано")
    Instant createdAt,

    @Schema(description = "Обновлено")
    Instant updatedAt
) {
}
