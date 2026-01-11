package ru.ryatronth.sd.ticket.service.ticket.engine.impl.transfer_to_parent;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.department.dto.department.DepartmentDto;
import ru.ryatronth.sd.iamsync.dto.IamUserDto;
import ru.ryatronth.sd.ticket.adapter.DepartmentClientAdapter;
import ru.ryatronth.sd.ticket.adapter.IamUsersClientAdapter;
import ru.ryatronth.sd.ticket.domain.assignment.TicketCategoryAssigneeEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketDepartmentSnapshotMapper;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketUserSnapshotMapper;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;

@Component
@RequiredArgsConstructor
public class TransferTicketToParentDepartmentHandler
    implements OnTicketHandler<TransferTicketToParentDepartmentCommand, TransferTicketToParentDepartmentResult> {

  private static final String DEFAULT_COMMENT = "Заявка переведена к исполнителю в родительском филиале";

  private final TicketEntityRepository ticketEntityRepository;
  private final TicketCategoryAssigneeEntityRepository assigneeRepo;

  private final DepartmentClientAdapter departmentClientAdapter;
  private final IamUsersClientAdapter iamUsersClientAdapter;

  private final TicketDepartmentSnapshotMapper ticketDepartmentSnapshotMapper;
  private final TicketUserSnapshotMapper ticketUserSnapshotMapper;

  @Override
  public Class<TransferTicketToParentDepartmentCommand> supports() {
    return TransferTicketToParentDepartmentCommand.class;
  }

  @Override
  public HandlerResult<TransferTicketToParentDepartmentResult> handle(OnTicketContext ctx,
                                                                      TransferTicketToParentDepartmentCommand command) {
    TicketEntity ticket = ctx.ticketOrThrow();

    UUID fromDeptId = ticket.getCurrentDepartmentId();

    DepartmentDto parentDept = departmentClientAdapter.getParentOrThrow(fromDeptId);
    UUID toDeptId = parentDept.getId();

    var fromDeptDomainSnapshot = ticket.getCurrentDepartmentSnapshot();

    UUID oldAssigneeId = ticket.getAssigneeUserId();
    var oldAssigneeDomainSnapshot = ticket.getAssigneeSnapshot();

    var toDeptDomainSnapshot = ticketDepartmentSnapshotMapper.toDomainSnapshot(parentDept);

    ticket.setCurrentDepartmentId(toDeptId);
    ticket.setCurrentDepartmentSnapshot(toDeptDomainSnapshot);

    UUID categoryId = ticket.getCategory().getId();
    var candidates = assigneeRepo.findAllFor(categoryId, toDeptId);

    UUID newAssigneeId = null;
    UserSnapshot newAssigneeDomainSnapshot = null;

    if (!candidates.isEmpty()) {
      newAssigneeId = candidates.getFirst().getUserId();

      IamUserDto iamUser = iamUsersClientAdapter.getByIdOrThrow(newAssigneeId);

      newAssigneeDomainSnapshot = ticketUserSnapshotMapper.toDomainSnapshot(iamUser);

      ticket.setAssigneeUserId(newAssigneeId);
      ticket.setAssigneeSnapshot(newAssigneeDomainSnapshot);
    }

    ticket = ticketEntityRepository.save(ticket);

    String comment = (command.request() == null || command.request().comment() == null)
        ? DEFAULT_COMMENT
        : command.request().comment();

    var payload = new TransferTicketToParentDepartmentEventPayload(
        fromDeptDomainSnapshot,
        toDeptDomainSnapshot,
        oldAssigneeId,
        newAssigneeId,
        oldAssigneeDomainSnapshot,
        newAssigneeDomainSnapshot
    );

    var event = new TicketHistoryEvent(
        TicketEventType.ASSIGNEE_CHANGED,
        payload,
        comment
    );

    var result = new TransferTicketToParentDepartmentResult(
        ticket
    );

    return HandlerResult.of(result, event);
  }

}
