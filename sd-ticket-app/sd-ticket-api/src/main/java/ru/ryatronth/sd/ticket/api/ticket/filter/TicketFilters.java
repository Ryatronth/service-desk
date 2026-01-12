package ru.ryatronth.sd.ticket.api.ticket.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TicketStatus;

@Schema(name = "TicketFilters", description = "Фильтры поиска заявок")
public record TicketFilters(

    @Schema(description = "Поисковая строка (LIKE) по number/title/description")
    String q,

    @Schema(description = "Статус")
    TicketStatus status,

    @Schema(description = "Приоритет")
    TicketPriority priority,

    @Schema(description = "UUID категории")
    UUID categoryId,

    @Schema(description = "UUID филиала отправителя")
    UUID requesterUserId,

    @Schema(description = "UUID текущего исполнителя")
    UUID assigneeUserId,

    @Schema(description = "Дедлайн от (>=)")
    Instant dueFrom,

    @Schema(description = "Дедлайн до (<=)")
    Instant dueTo,

    @Schema(description = "Режим объединения условий: AND/OR")
    SearchMode mode
) {
}
