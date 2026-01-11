package ru.ryatronth.sd.ticket.service.ticket.engine.impl.resolve;

import java.time.Instant;
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
public class ResolveTicketHandler implements OnTicketHandler<ResolveTicketCommand, ResolveTicketResult> {

  private static final String DEFAULT_COMMENT = "Работа по обращению завершена";

  private final TicketEntityRepository ticketEntityRepository;

  @Override
  public Class<ResolveTicketCommand> supports() {
    return ResolveTicketCommand.class;
  }

  @Override
  public HandlerResult<ResolveTicketResult> handle(OnTicketContext ctx, ResolveTicketCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    ticket.setStatus(TicketStatus.RESOLVED);
    ticket.setClosedAt(Instant.now());

    ticket.setSpentTimeMinutes(command.request().spentTimeMinutes());
    ticket.setSpentMoney(command.request().spentMoney());

    ticket = ticketEntityRepository.save(ticket);

    String comment = command.request().comment() == null ? DEFAULT_COMMENT : command.request().comment();

    var payload = new ResolveTicketEventPayload(
        command.request().spentTimeMinutes(),
        command.request().spentMoney()
    );

    var event = new TicketHistoryEvent(TicketEventType.RESOLVED, payload, comment);
    var result = new ResolveTicketResult(ticket);

    return HandlerResult.of(result, event);
  }
}
