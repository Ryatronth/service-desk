package ru.ryatronth.sd.ticket.api.category.filters;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@Schema(description = "Фильтры категорий обращений")
@Builder
public record TicketCategoryFilters(

    @Schema(description = "Поиск по названию или описанию (LIKE)")
    String q,

    @Schema(description = "Фильтр по приоритету")
    TicketPriority priority
) {
}
