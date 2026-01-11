package ru.ryatronth.sd.ticket.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@Schema(description = "Запрос на обновление категории обращения")
public record TicketCategoryUpdateRequest(

    @Schema(description = "Название категории") String name,

    @Schema(description = "Описание категории") String description,

    @Schema(description = "Приоритет категории") TicketPriority priority,

    @Schema(description = "Ожидаемое время выполнения") @Positive Long expectedDurationMinutes) {
}
