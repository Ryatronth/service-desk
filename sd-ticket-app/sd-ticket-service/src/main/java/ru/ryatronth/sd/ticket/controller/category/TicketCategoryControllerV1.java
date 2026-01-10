package ru.ryatronth.sd.ticket.controller.category;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.ticket.api.category.TicketCategoryApiV1;
import ru.ryatronth.sd.ticket.api.category.filters.TicketCategoryFilters;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryCreateRequest;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryDto;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryUpdateRequest;
import ru.ryatronth.sd.ticket.mapper.category.TicketCategoryMapper;
import ru.ryatronth.sd.ticket.service.category.TicketCategoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping(TicketCategoryApiV1.BASE_PATH)
public class TicketCategoryControllerV1 implements TicketCategoryApiV1 {

  private final TicketCategoryService service;

  private final TicketCategoryMapper mapper;

  @Override
  public ResponseEntity<TicketCategoryDto> create(TicketCategoryCreateRequest request) {
    return ResponseEntity.status(201).body(mapper.toDto(service.create(request)));
  }

  @Override
  public ResponseEntity<TicketCategoryDto> getById(UUID id) {
    return ResponseEntity.ok(mapper.toDto(service.getById(id)));
  }

  @Override
  public ResponseEntity<Page<TicketCategoryDto>> getAll(TicketCategoryFilters filters,
                                                        Pageable pageable) {
    return ResponseEntity.ok(service.getAll(filters, pageable).map(mapper::toDto));
  }

  @Override
  public ResponseEntity<TicketCategoryDto> update(UUID id, TicketCategoryUpdateRequest request) {
    return ResponseEntity.ok(mapper.toDto(service.update(id, request)));
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
