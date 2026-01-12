package ru.ryatronth.sd.ticket.controller.assignment;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.ticket.api.assignment.TicketCategoryAssigneeApiV1;
import ru.ryatronth.sd.ticket.api.assignment.filter.TicketCategoryAssigneeFilters;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeCreateRequest;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeUpdateRequest;
import ru.ryatronth.sd.ticket.mapper.assignment.TicketCategoryAssigneeMapper;
import ru.ryatronth.sd.ticket.service.assignment.TicketCategoryAssigneeService;

@RequiredArgsConstructor
@RestController
@RequestMapping(TicketCategoryAssigneeApiV1.BASE_PATH)
public class TicketCategoryAssigneeControllerV1 implements TicketCategoryAssigneeApiV1 {

  private final TicketCategoryAssigneeService service;
  private final TicketCategoryAssigneeMapper mapper;

  @Override
  public ResponseEntity<List<TicketCategoryAssigneeDto>> search(TicketCategoryAssigneeFilters filters, Pageable pageable) {
    var entities = service.search(filters, pageable);
    var dtos = entities.stream().map(mapper::toDto).toList();
    return ResponseEntity.ok(dtos);
  }

  @Override
  public ResponseEntity<TicketCategoryAssigneeDto> getById(UUID id) {
    var entity = service.getByIdOrThrow(id);
    return ResponseEntity.ok(mapper.toDto(entity));
  }

  @Override
  public ResponseEntity<TicketCategoryAssigneeDto> create(TicketCategoryAssigneeCreateRequest request) {
    var created = service.create(request);
    var dto = mapper.toDto(service.getByIdOrThrow(created.getId())); // гарантируем category join
    return ResponseEntity
        .created(URI.create(TicketCategoryAssigneeApiV1.BASE_PATH + "/" + dto.id()))
        .body(dto);
  }

  @Override
  public ResponseEntity<TicketCategoryAssigneeDto> update(UUID id, TicketCategoryAssigneeUpdateRequest request) {
    var updated = service.update(id, request);
    return ResponseEntity.ok(mapper.toDto(updated));
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
