package ru.ryatronth.sd.ticket.service.ticket.engine.impl.transfer_to_parent;

import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public record TransferTicketToParentDepartmentResult(
    TicketEntity ticket
) {
}
