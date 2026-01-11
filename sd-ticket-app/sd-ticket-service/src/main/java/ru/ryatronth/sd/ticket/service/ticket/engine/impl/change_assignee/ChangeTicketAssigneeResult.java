package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_assignee;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record ChangeTicketAssigneeResult(
    TicketEntity ticket
) {
}
