package ru.ryatronth.sd.ticket.api.assignment.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.dto.assignment.TicketAssignmentType;

@Schema(name = "TicketCategoryAssigneeFilters", description = "Фильтры поиска назначений исполнителей/менеджеров")
public record TicketCategoryAssigneeFilters(

    @Schema(description = "UUID департамента")
    UUID departmentId,

    @Schema(description = "UUID категории (для MANAGER всегда null, для EXECUTOR всегда != null)")
    UUID categoryId,

    @Schema(description = "UUID пользователя")
    UUID userId,

    @Schema(description = "Тип назначения: EXECUTOR или MANAGER")
    TicketAssignmentType assignmentType,

    @Schema(description = "Режим объединения условий: AND/OR")
    SearchMode mode
) {}
