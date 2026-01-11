package ru.ryatronth.sd.ticket.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@Schema(description = "Запрос на создание категории обращения")
public record TicketCategoryCreateRequest(

    @Schema(description = "Название категории", requiredMode = Schema.RequiredMode.REQUIRED) @NotBlank String name,

    @Schema(description = "Описание категории") String description,

    @Schema(description = "Приоритет категории", requiredMode = Schema.RequiredMode.REQUIRED) @NotNull TicketPriority priority,

    @Schema(description = "Ожидаемое время выполнения", example = "4") @NotNull @Positive Long expectedDurationMinutes) {
}
