package ru.ryatronth.sd.ticket.service.ticket;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.api.ticket.filter.TicketFilters;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketSpecifications;
import ru.ryatronth.sd.ticket.dto.ticket.TicketAssigneeChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketCreateRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketPriorityChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketResolveRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketSlaChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketStatusChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketTransferToParenDepartmentRequest;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.TicketEngine;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_assignee.ChangeTicketAssigneeCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_priority.ChangeTicketPriorityCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_sla.ChangeTicketSlaCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_status.ChangeTicketStatusCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.create.CreateTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.resolve.ResolveTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.impl.transfer_to_parent.TransferTicketToParentDepartmentCommand;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketEntityRepository ticketRepository;
  private final TicketEngine engine;

  @Transactional(readOnly = true)
  public TicketEntity getById(UUID id) {
    return ticketRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Заявка не найдена: " + id));
  }

  @Transactional(readOnly = true)
  public Page<TicketEntity> getAll(TicketFilters filters, Pageable pageable) {
    var spec = TicketSpecifications.Filter.builder()
        .q(filters == null ? null : filters.q())
        .status(filters == null ? null : filters.status())
        .priority(filters == null ? null : filters.priority())
        .categoryId(filters == null ? null : filters.categoryId())
        .requesterDepartmentId(filters == null ? null : filters.requesterDepartmentId())
        .assigneeUserId(filters == null ? null : filters.assigneeUserId())
        .dueFrom(filters == null ? null : filters.dueFrom())
        .dueTo(filters == null ? null : filters.dueTo())
        .mode(filters == null || filters.mode() == null ? SearchMode.AND : filters.mode())
        .build()
        .toSpec();

    return ticketRepository.findAll(spec, pageable);
  }

  @Transactional
  public TicketEntity create(TicketCreateRequest request) {
    return engine.executeNoTicket(new CreateTicketCommand(request)).ticket();
  }

  @Transactional
  public TicketEntity changeAssignee(UUID id, TicketAssigneeChangeRequest request) {
    return engine.executeOnTicket(new ChangeTicketAssigneeCommand(id, request)).ticket();
  }

  @Transactional
  public TicketEntity transferToParent(UUID id, TicketTransferToParenDepartmentRequest request) {
    return engine.executeOnTicket(new TransferTicketToParentDepartmentCommand(id, request)).ticket();
  }

  @Transactional
  public TicketEntity changeStatus(UUID id, TicketStatusChangeRequest request) {
    return engine.executeOnTicket(new ChangeTicketStatusCommand(id, request)).ticket();
  }

  @Transactional
  public TicketEntity changePriority(UUID id, TicketPriorityChangeRequest request) {
    return engine.executeOnTicket(new ChangeTicketPriorityCommand(id, request)).ticket();
  }

  @Transactional
  public TicketEntity changeSla(UUID id, TicketSlaChangeRequest request) {
    return engine.executeOnTicket(new ChangeTicketSlaCommand(id, request)).ticket();
  }

  @Transactional
  public TicketEntity resolve(UUID id, TicketResolveRequest request) {
    return engine.executeOnTicket(new ResolveTicketCommand(id, request)).ticket();
  }
}
