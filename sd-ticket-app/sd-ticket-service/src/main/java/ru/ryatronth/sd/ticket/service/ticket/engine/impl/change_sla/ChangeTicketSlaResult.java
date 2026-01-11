package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_sla;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record ChangeTicketSlaResult(
    TicketEntity ticket
) {
}
