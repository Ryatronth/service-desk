package ru.ryatronth.sd.ticket.service.ticket.engine.impl.create;

import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ryatronth.sd.department.dto.department.DepartmentShortDto;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.ticket.adapter.DepartmentClientAdapter;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.number.TicketNumberGenerator;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.CategorySnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.DepartmentSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.WorkplaceSnapshot;
import ru.ryatronth.sd.ticket.dto.TicketStatus;
import ru.ryatronth.sd.ticket.dto.attachment.TicketAttachmentCreateRequest;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.dto.ticket.TicketCreateRequest;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketDepartmentSnapshotMapper;
import ru.ryatronth.sd.ticket.service.attachment.TicketAttachmentService;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.NoTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.NoTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.TicketHistoryEvent;
import ru.ryatronth.sd.ticket.util.TicketUtil;

@Component
@RequiredArgsConstructor
public class CreateTicketHandler implements NoTicketHandler<CreateTicketCommand, CreateTicketResult> {

  private static final String COMMENT = "Обращение создано";

  private final TicketEntityRepository ticketEntityRepository;
  private final TicketCategoryEntityRepository ticketCategoryEntityRepository;
  private final TicketNumberGenerator ticketNumberGenerator;
  private final TicketAttachmentService ticketAttachmentService;

  private final DepartmentClientAdapter departmentClientAdapter;
  private final TicketDepartmentSnapshotMapper ticketDepartmentSnapshotMapper;

  @Override
  public Class<CreateTicketCommand> supports() {
    return CreateTicketCommand.class;
  }

  @Override
  public HandlerResult<CreateTicketResult> handle(NoTicketContext ctx, CreateTicketCommand command) {
    var ticket = buildEntity(ctx, command.request());

    for (TicketAttachmentCreateRequest attachment : command.request().attachments()) {
      ticketAttachmentService.addToTicket(ticket.getId(), attachment);
    }

    ctx.bindTicket(ticket);

    var event = new TicketHistoryEvent(
        TicketEventType.CREATED,
        new CreateTicketEventPayload(ticket.getNumber(), ticket.getTitle()),
        COMMENT
    );

    return HandlerResult.of(new CreateTicketResult(ticket), event);
  }

  private TicketEntity buildEntity(NoTicketContext ctx, TicketCreateRequest request) {
    CurrentUser cu = ctx.actor();

    var category = ticketCategoryEntityRepository.findById(request.categoryId())
        .orElseThrow(() -> new NotFoundException("Категория не найдена: " + request.categoryId()));

    DepartmentShortDto department = departmentClientAdapter.findFirstByCode(cu.departmentCode());
    DepartmentSnapshot deptSnapshot = ticketDepartmentSnapshotMapper.toDomainSnapshot(department);

    var entity = new TicketEntity();

    entity.setNumber(ticketNumberGenerator.generate());
    entity.setTitle(request.title().trim());
    entity.setDescription(request.description().trim());

    entity.setCategory(category);
    entity.setCategorySnapshot(CategorySnapshot.builder().name(category.getName()).build());

    entity.setPriority(category.getPriority());
    entity.setDueAt(TicketUtil.calcDueAt(Instant.now(), category.getExpectedDurationMinutes()));

    entity.setStatus(TicketStatus.NEW);

    entity.setRequesterUserId(UUID.fromString(cu.userId()));
    entity.setRequesterSnapshot(
        UserSnapshot.builder()
            .username(cu.username())
            .displayName(cu.buildFullName())
            .email(cu.email())
            .build()
    );

    entity.setRequesterDepartmentId(department.getId());
    entity.setRequesterDepartmentSnapshot(deptSnapshot);
    entity.setCurrentDepartmentId(department.getId());
    entity.setCurrentDepartmentSnapshot(deptSnapshot);

    entity.setRequesterWorkplaceSnapshot(WorkplaceSnapshot.builder().code(cu.workplaceCode()).build());

    return ticketEntityRepository.save(entity);
  }
}
