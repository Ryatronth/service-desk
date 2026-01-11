package ru.ryatronth.sd.ticket.service.ticket.engine.impl.add_comment;

import java.util.UUID;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentCreateRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;

public record AddTicketCommentCommand(
    UUID ticketId,
    TicketCommentCreateRequest request
) implements OnTicketCommand<AddTicketCommentResult> {

  @Override
  public UUID ticketId() {
    return ticketId;
  }
}
