package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_priority;

import ru.ryatronth.sd.ticket.dto.TicketPriority;

public record ChangeTicketPriorityEventPayload(
    TicketPriority oldPriority,
    TicketPriority newPriority
) {
}
