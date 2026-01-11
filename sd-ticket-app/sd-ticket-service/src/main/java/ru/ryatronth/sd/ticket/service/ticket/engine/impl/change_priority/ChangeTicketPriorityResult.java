package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_priority;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record ChangeTicketPriorityResult(
    TicketEntity ticket
) {
}
