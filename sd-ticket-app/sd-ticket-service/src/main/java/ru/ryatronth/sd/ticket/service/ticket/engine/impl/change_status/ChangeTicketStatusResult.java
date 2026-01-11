package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_status;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record ChangeTicketStatusResult(
    TicketEntity ticket
) {
}
