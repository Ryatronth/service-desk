package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_assignee;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.adapter.IamUsersClientAdapter;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketUserSnapshotMapper;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class ChangeTicketAssigneeHandler
    implements OnTicketHandler<ChangeTicketAssigneeCommand, ChangeTicketAssigneeResult> {

  private static final String DEFAULT_COMMENT = "Изменен ответственный по обращению";

  private final TicketEntityRepository ticketEntityRepository;

  private final IamUsersClientAdapter iamUsersClientAdapter;
  private final TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Override
  public Class<ChangeTicketAssigneeCommand> supports() {
    return ChangeTicketAssigneeCommand.class;
  }

  @Override
  public HandlerResult<ChangeTicketAssigneeResult> handle(OnTicketContext ctx, ChangeTicketAssigneeCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    UUID from = ticket.getAssigneeUserId();
    UserSnapshot oldSnapshot = ticket.getAssigneeSnapshot();
    UUID to = command.request().assigneeUserId();

    ticket.setAssigneeUserId(to);
    var newSnapshot = ticketUserSnapshotMapper.toDomainSnapshot(iamUsersClientAdapter.getByIdOrThrow(to));
    ticket.setAssigneeSnapshot(newSnapshot);

    ticket = ticketEntityRepository.save(ticket);

    String comment = command.request().comment() == null ? DEFAULT_COMMENT : command.request().comment();

    var payload = new ChangeTicketAssigneeEventPayload(from, oldSnapshot, to, newSnapshot);
    var event = new TicketHistoryEvent(
        TicketEventType.ASSIGNEE_CHANGED,
        payload,
        comment
    );

    var result = new ChangeTicketAssigneeResult(ticket);
    return HandlerResult.of(result, event);
  }

}
