package ru.ryatronth.sd.ticket.controller.assignment;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.ticket.api.assignment.TicketCategoryAssigneeApiV1;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneesUpdateRequest;
import ru.ryatronth.sd.ticket.mapper.assignment.TicketCategoryAssigneeMapper;
import ru.ryatronth.sd.ticket.service.assignment.TicketCategoryAssigneeService;

@RequiredArgsConstructor
@RestController
@RequestMapping(TicketCategoryAssigneeApiV1.BASE_PATH)
public class TicketCategoryAssigneeControllerV1 implements TicketCategoryAssigneeApiV1 {

  private final TicketCategoryAssigneeService service;
  private final TicketCategoryAssigneeMapper mapper;

  @Override
  public ResponseEntity<List<TicketCategoryAssigneeDto>> getAssignees(UUID categoryId,
                                                                      UUID departmentId) {
    return ResponseEntity.ok(
        service.getAssignees(categoryId, departmentId).stream().map(mapper::toDto).toList());
  }

  @Override
  public ResponseEntity<List<TicketCategoryAssigneeDto>> updateAssignees(UUID categoryId,
                                                                         TicketCategoryAssigneesUpdateRequest request) {
    var saved = service.replaceAssignees(categoryId, request.departmentId(), request.userIds());
    return ResponseEntity.ok(saved.stream().map(mapper::toDto).toList());
  }

  @Override
  public ResponseEntity<Void> deleteAssignees(UUID categoryId, UUID departmentId) {
    service.deleteAssignees(categoryId, departmentId);
    return ResponseEntity.noContent().build();
  }
}
