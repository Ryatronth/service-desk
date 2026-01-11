package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_status;

import ru.ryatronth.sd.ticket.dto.TicketStatus;

public record ChangeTicketStatusEventPayload(
    TicketStatus from,
    TicketStatus to
) {
}
