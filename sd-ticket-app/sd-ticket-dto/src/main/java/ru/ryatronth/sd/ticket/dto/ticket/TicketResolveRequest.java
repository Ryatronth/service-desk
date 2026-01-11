package ru.ryatronth.sd.ticket.dto.ticket;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Schema(name = "TicketResolveRequest", description = "Завершение заявки")
public record TicketResolveRequest(

    @NotNull
    @Schema(description = "Затраченное время (в минутах)", requiredMode = Schema.RequiredMode.REQUIRED, example = "90")
    Long spentTimeMinutes,

    @Schema(description = "Затраченные средства", example = "150000")
    BigDecimal spentMoney,

    @Schema(description = "Комментарий к завершению", example = "Заменили кабель, проверили подключение")
    String comment
) {
}
