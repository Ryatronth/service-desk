package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_priority;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketPriorityChangeRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record ChangeTicketPriorityCommand(
    UUID ticketId,
    TicketPriorityChangeRequest request
) implements OnTicketCommand<ChangeTicketPriorityResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
