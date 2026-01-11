package ru.ryatronth.sd.ticket.service.ticket.engine.impl.auto_assign;

import java.util.UUID;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record AutoAssignTicketCommand(UUID ticketId)
    implements OnTicketCommand<AutoAssignTicketResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
