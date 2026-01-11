package ru.ryatronth.sd.ticket.service.ticket.engine.impl.auto_assign;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.ticket.adapter.IamUsersClientAdapter;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketUserSnapshotMapper;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class AutoAssignTicketHandler implements OnTicketHandler<AutoAssignTicketCommand, AutoAssignTicketResult> {

  private static final String COMMENT_ASSIGNED = "Исполнитель назначен автоматически";
  private static final String COMMENT_NOT_FOUND = "Автоназначение: исполнитель не найден.";

  private final TicketEntityRepository ticketEntityRepository;
  private final TicketCategoryAssigneeEntityRepository assigneeRepo;

  private final IamUsersClientAdapter iamUsersClientAdapter;
  private final TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Override
  public Class<AutoAssignTicketCommand> supports() {
    return AutoAssignTicketCommand.class;
  }

  @Override
  public HandlerResult<AutoAssignTicketResult> handle(OnTicketContext ctx, AutoAssignTicketCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    if (ticket.getAssigneeUserId() != null) {
      return HandlerResult.of(new AutoAssignTicketResult(ticket));
    }

    UUID categoryId = ticket.getCategory().getId();
    UUID departmentId = ticket.getCurrentDepartmentId();

    var candidates = assigneeRepo.findAllFor(categoryId, departmentId);
    if (candidates.isEmpty()) {
      var result = new AutoAssignTicketResult(ticket);
      var event = new TicketHistoryEvent(
          TicketEventType.ASSIGNEE_CHANGED,
          new AutoAssignTicketEventPayload(null),
          COMMENT_NOT_FOUND
      );
      return HandlerResult.of(result, event);
    }

    UUID assigneeUserId = candidates.getFirst().getUserId();

    ticket.setAssigneeUserId(assigneeUserId);
    var userSnapshot = ticketUserSnapshotMapper.toDomainSnapshot(iamUsersClientAdapter.getByIdOrThrow(assigneeUserId));
    ticket.setAssigneeSnapshot(userSnapshot);

    ticket = ticketEntityRepository.save(ticket);

    var payload = new AutoAssignTicketEventPayload(assigneeUserId);
    var event = new TicketHistoryEvent(
        TicketEventType.ASSIGNEE_CHANGED,
        payload,
        COMMENT_ASSIGNED
    );

    return HandlerResult.of(new AutoAssignTicketResult(ticket), event);
  }
}
