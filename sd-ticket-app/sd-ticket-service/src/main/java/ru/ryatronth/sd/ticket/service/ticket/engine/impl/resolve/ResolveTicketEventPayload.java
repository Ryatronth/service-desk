package ru.ryatronth.sd.ticket.service.ticket.engine.impl.resolve;

import java.math.BigDecimal;

public record ResolveTicketEventPayload(
    Long spentTimeMinutes,
    BigDecimal spentMoney
) {
}
