package ru.ryatronth.sd.ticket.service.ticket.engine.impl.resolve;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record ResolveTicketResult(
    TicketEntity ticket
) {
}
