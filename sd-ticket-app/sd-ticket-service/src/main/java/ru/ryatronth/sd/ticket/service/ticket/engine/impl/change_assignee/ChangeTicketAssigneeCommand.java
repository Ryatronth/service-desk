package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_assignee;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketAssigneeChangeRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record ChangeTicketAssigneeCommand(
    UUID ticketId,
    TicketAssigneeChangeRequest request
) implements OnTicketCommand<ChangeTicketAssigneeResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
