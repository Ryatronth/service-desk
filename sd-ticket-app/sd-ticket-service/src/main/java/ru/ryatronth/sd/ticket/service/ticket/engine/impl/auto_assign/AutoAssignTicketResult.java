package ru.ryatronth.sd.ticket.service.ticket.engine.impl.auto_assign;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record AutoAssignTicketResult(
    TicketEntity ticket
) {
}
