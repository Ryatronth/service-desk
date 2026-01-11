package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_status;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketStatusChangeRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record ChangeTicketStatusCommand(
    UUID ticketId,
    TicketStatusChangeRequest request
) implements OnTicketCommand<ChangeTicketStatusResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
