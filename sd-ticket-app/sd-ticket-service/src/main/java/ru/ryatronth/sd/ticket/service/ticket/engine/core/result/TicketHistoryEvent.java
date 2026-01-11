package ru.ryatronth.sd.ticket.service.ticket.engine.core.result;

import ru.ryatronth.sd.ticket.dto.event.TicketEventType;

public record TicketHistoryEvent(
    TicketEventType type,
    Object payload,
    String comment
) {
}

