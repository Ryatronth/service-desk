package ru.ryatronth.sd.ticket.service.ticket.engine.impl.resolve;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketResolveRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record ResolveTicketCommand(
    UUID ticketId,
    TicketResolveRequest request
) implements OnTicketCommand<ResolveTicketResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
