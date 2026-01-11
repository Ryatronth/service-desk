package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_priority;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class ChangeTicketPriorityHandler
    implements OnTicketHandler<ChangeTicketPriorityCommand, ChangeTicketPriorityResult> {

  private static final String COMMENT = "Изменен приоритет обращения";

  private final TicketEntityRepository ticketEntityRepository;

  @Override
  public Class<ChangeTicketPriorityCommand> supports() {
    return ChangeTicketPriorityCommand.class;
  }

  @Override
  public HandlerResult<ChangeTicketPriorityResult> handle(OnTicketContext ctx, ChangeTicketPriorityCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    TicketPriority from = ticket.getPriority();
    TicketPriority to = command.request().priority();

    ticket.setPriority(to);
    ticket = ticketEntityRepository.save(ticket);

    var payload = new ChangeTicketPriorityEventPayload(from, to);
    var event = new TicketHistoryEvent(TicketEventType.PRIORITY_CHANGED, payload, COMMENT);

    var result = new ChangeTicketPriorityResult(ticket);
    return HandlerResult.of(result, event);
  }
}
