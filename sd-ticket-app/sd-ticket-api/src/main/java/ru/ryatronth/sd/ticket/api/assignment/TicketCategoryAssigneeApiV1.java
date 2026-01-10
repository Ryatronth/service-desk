package ru.ryatronth.sd.ticket.api.assignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneesUpdateRequest;

@Tag(name = "Ticket Category Assignees", description = "Управление исполнителями категорий обращений в разрезе филиалов (department.id)")
@RequestMapping(TicketCategoryAssigneeApiV1.BASE_PATH)
public interface TicketCategoryAssigneeApiV1 {

  String BASE_PATH = "/api/v1/ticket-categories";

  @Operation(summary = "Получить исполнителей категории в филиале")
  @GetMapping("/{categoryId}/assignees")
  ResponseEntity<List<TicketCategoryAssigneeDto>> getAssignees(
      @PathVariable @Parameter(description = "ID категории") UUID categoryId,
      @Parameter(description = "ID филиала (department.id)") @RequestParam("departmentId")
      UUID departmentId);

  @Operation(summary = "Задать исполнителей категории в филиале", description = "Полностью заменяет список исполнителей для категории в указанном филиале")
  @PutMapping("/{categoryId}/assignees")
  ResponseEntity<List<TicketCategoryAssigneeDto>> updateAssignees(@PathVariable UUID categoryId,
                                                                  @RequestBody
                                                                  TicketCategoryAssigneesUpdateRequest request);

  @Operation(summary = "Удалить все назначения категории в филиале")
  @DeleteMapping("/{categoryId}/assignees")
  ResponseEntity<Void> deleteAssignees(@PathVariable UUID categoryId,
                                       @RequestParam("departmentId") UUID departmentId);
}
