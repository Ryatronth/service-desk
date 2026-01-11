package ru.ryatronth.sd.ticket.service.ticket.engine.impl.create;

import ru.ryatronth.sd.ticket.dto.ticket.TicketCreateRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.NoTicketCommand;

public record CreateTicketCommand(TicketCreateRequest request)
    implements NoTicketCommand<CreateTicketResult> {
}
