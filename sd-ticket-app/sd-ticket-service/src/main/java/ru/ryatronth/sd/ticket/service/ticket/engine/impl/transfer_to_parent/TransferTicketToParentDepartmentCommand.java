package ru.ryatronth.sd.ticket.service.ticket.engine.impl.transfer_to_parent;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.ticket.TicketTransferToParenDepartmentRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record TransferTicketToParentDepartmentCommand(
    UUID ticketId,
    TicketTransferToParenDepartmentRequest request
) implements OnTicketCommand<TransferTicketToParentDepartmentResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
