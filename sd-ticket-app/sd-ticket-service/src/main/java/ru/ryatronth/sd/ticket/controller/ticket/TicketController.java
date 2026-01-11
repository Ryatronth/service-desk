package ru.ryatronth.sd.ticket.controller.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.ryatronth.sd.ticket.api.ticket.TicketApiV1;
import ru.ryatronth.sd.ticket.api.ticket.filter.TicketFilters;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.dto.ticket.TicketAssigneeChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketCreateRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketDto;
import ru.ryatronth.sd.ticket.dto.ticket.TicketPriorityChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketResolveRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketShortDto;
import ru.ryatronth.sd.ticket.dto.ticket.TicketSlaChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketStatusChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketTransferToParenDepartmentRequest;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketMapper;
import ru.ryatronth.sd.ticket.service.ticket.TicketService;

@RestController
@RequestMapping(TicketApiV1.BASE_PATH)
@RequiredArgsConstructor
public class TicketController implements TicketApiV1 {

  private final TicketService ticketService;
  private final TicketMapper ticketMapper;

  @Override
  public ResponseEntity<TicketDto> create(TicketCreateRequest request) {
    TicketEntity ticket = ticketService.create(request);

    var location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(ticket.getId())
        .toUri();

    return ResponseEntity.created(location).body(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> getById(java.util.UUID id) {
    var ticket = ticketService.getById(id);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<Page<TicketShortDto>> getAll(TicketFilters filters, Pageable pageable) {
    var page = ticketService.getAll(filters, pageable).map(ticketMapper::toShortDto);
    return ResponseEntity.ok(page);
  }

  @Override
  public ResponseEntity<TicketDto> changeAssignee(java.util.UUID id, TicketAssigneeChangeRequest request) {
    var ticket = ticketService.changeAssignee(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> transferToParent(java.util.UUID id, TicketTransferToParenDepartmentRequest request) {
    var ticket = ticketService.transferToParent(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> changeStatus(java.util.UUID id, TicketStatusChangeRequest request) {
    var ticket = ticketService.changeStatus(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> changePriority(java.util.UUID id, TicketPriorityChangeRequest request) {
    var ticket = ticketService.changePriority(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> changeSla(java.util.UUID id, TicketSlaChangeRequest request) {
    var ticket = ticketService.changeSla(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }

  @Override
  public ResponseEntity<TicketDto> resolve(java.util.UUID id, TicketResolveRequest request) {
    var ticket = ticketService.resolve(id, request);
    return ResponseEntity.ok(ticketMapper.toDto(ticket));
  }
}
