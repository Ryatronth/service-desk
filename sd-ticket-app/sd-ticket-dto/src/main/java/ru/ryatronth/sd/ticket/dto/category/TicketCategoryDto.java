package ru.ryatronth.sd.ticket.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TimeUnit;

@Schema(description = "Категория обращения")
@Builder
public record TicketCategoryDto(

    @Schema(description = "ID категории", example = "a3f9c2f2-6d8b-4f01-9b3e-9d4e0c9a2f31") UUID id,

    @Schema(description = "Название категории", example = "Проблемы с доступом") String name,

    @Schema(description = "Описание категории", example = "Ошибки входа, блокировки учетных записей") String description,

    @Schema(description = "Приоритет категории") TicketPriority priority,

    @Schema(description = "Ожидаемое время выполнения") Long expectedDuration,

    @Schema(description = "Единицы измерения времени выполнения") TimeUnit expectedDurationUnit,

    @Schema(description = "Дата создания") Instant createdAt,

    @Schema(description = "Дата обновления") Instant updatedAt) {
}
