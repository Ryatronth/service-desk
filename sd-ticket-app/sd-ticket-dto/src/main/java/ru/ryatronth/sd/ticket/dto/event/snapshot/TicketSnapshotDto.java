package ru.ryatronth.sd.ticket.dto.event.snapshot;

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

@Schema(
    name = "TicketSnapshotDto",
    description = "Снимок состояния заявки на момент события"
)
public record TicketSnapshotDto(

    @Schema(description = "ID заявки")
    UUID id,

    @Schema(description = "Номер заявки")
    String number,

    @Schema(description = "Заголовок заявки")
    String title,

    @Schema(description = "Описание заявки")
    String description,

    @Schema(description = "Снимок категории")
    CategorySnapshotDto category,

    @Schema(description = "Приоритет заявки")
    TicketPriority priority,

    @Schema(description = "Дедлайн (SLA)")
    Instant dueAt,

    @Schema(description = "Статус заявки")
    TicketStatus status,

    @Schema(description = "Запросивший пользователь")
    UserSnapshotDto requester,

    @Schema(description = "Подразделение заявителя")
    DepartmentSnapshotDto requesterDepartment,

    @Schema(description = "Текущее подразделение обработки")
    DepartmentSnapshotDto currentDepartment,

    @Schema(description = "Рабочее место заявителя")
    WorkplaceSnapshotDto requesterWorkplace,

    @Schema(description = "Исполнитель заявки")
    UserSnapshotDto assignee,

    @Schema(description = "Дата закрытия заявки")
    Instant closedAt,

    @Schema(description = "Затраченное время (в минутах)")
    Long spentTimeMinutes,

    @Schema(description = "Затраченные деньги")
    BigDecimal spentMoney

) {
}
