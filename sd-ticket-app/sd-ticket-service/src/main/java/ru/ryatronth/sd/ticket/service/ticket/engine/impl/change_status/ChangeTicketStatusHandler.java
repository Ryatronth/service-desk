package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.TicketStatus;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class ChangeTicketStatusHandler implements OnTicketHandler<ChangeTicketStatusCommand, ChangeTicketStatusResult> {

  private static final String DEFAULT_COMMENT = "Статус обращения изменен";

  private final TicketEntityRepository ticketEntityRepository;

  @Override
  public Class<ChangeTicketStatusCommand> supports() {
    return ChangeTicketStatusCommand.class;
  }

  @Override
  public HandlerResult<ChangeTicketStatusResult> handle(OnTicketContext ctx, ChangeTicketStatusCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    TicketStatus from = ticket.getStatus();
    TicketStatus to = command.request().status();

    ticket.setStatus(to);
    ticket = ticketEntityRepository.save(ticket);

    String comment = command.request().comment() == null ? DEFAULT_COMMENT : command.request().comment();

    var payload = new ChangeTicketStatusEventPayload(from, to);
    var event = new TicketHistoryEvent(TicketEventType.STATUS_CHANGED, payload, comment);

    var result = new ChangeTicketStatusResult(ticket);
    return HandlerResult.of(result, event);
  }
}
