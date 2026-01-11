package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_sla;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class ChangeTicketSlaHandler implements OnTicketHandler<ChangeTicketSlaCommand, ChangeTicketSlaResult> {

  private static final String DEFAULT_COMMENT = "Изменен дедлайн обращения";

  private final TicketEntityRepository ticketEntityRepository;

  @Override
  public Class<ChangeTicketSlaCommand> supports() {
    return ChangeTicketSlaCommand.class;
  }

  @Override
  public HandlerResult<ChangeTicketSlaResult> handle(OnTicketContext ctx, ChangeTicketSlaCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    Instant oldDue = ticket.getDueAt();
    Instant newDue = command.request().dueAt();

    ticket.setDueAt(newDue);
    ticket = ticketEntityRepository.save(ticket);

    String comment = command.request().reason() == null ? DEFAULT_COMMENT : command.request().reason();

    var payload = new ChangeTicketSlaEventPayload(oldDue, newDue);
    var event = new TicketHistoryEvent(TicketEventType.SLA_CHANGED, payload, comment);

    var result = new ChangeTicketSlaResult(ticket);
    return HandlerResult.of(result, event);
  }
}
