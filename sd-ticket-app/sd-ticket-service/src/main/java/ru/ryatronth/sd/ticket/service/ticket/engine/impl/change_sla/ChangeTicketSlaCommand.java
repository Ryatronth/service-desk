package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_sla;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketSlaChangeRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record ChangeTicketSlaCommand(
    UUID ticketId,
    TicketSlaChangeRequest request
) implements OnTicketCommand<ChangeTicketSlaResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
