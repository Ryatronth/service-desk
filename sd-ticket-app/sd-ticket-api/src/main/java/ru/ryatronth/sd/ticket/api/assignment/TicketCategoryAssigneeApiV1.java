package ru.ryatronth.sd.ticket.api.assignment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.ryatronth.sd.ticket.api.assignment.filter.TicketCategoryAssigneeFilters;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeCreateRequest;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeDto;
import ru.ryatronth.sd.ticket.dto.assignment.TicketCategoryAssigneeUpdateRequest;

@Tag(name = "Ticket Category Assignees", description = "Назначения исполнителей/менеджеров для категорий обращений")
@RequestMapping(TicketCategoryAssigneeApiV1.BASE_PATH)
public interface TicketCategoryAssigneeApiV1 {

  String BASE_PATH = "/api/v1/ticket-category-assignees";

  @Operation(
      summary = "Поиск назначений",
      description = """
          Возвращает список назначений по фильтрам.

          Правила интерпретации:
          - MANAGER всегда имеет categoryId = null
          - EXECUTOR всегда имеет categoryId != null
          """
  )
  @ApiResponse(
      responseCode = "200",
      description = "Список назначений",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = TicketCategoryAssigneeDto.class)))
  )
  @GetMapping
  ResponseEntity<List<TicketCategoryAssigneeDto>> search(@ParameterObject TicketCategoryAssigneeFilters filters, @ParameterObject
  Pageable pageable);

  @Operation(summary = "Получить назначение по id")
  @ApiResponse(
      responseCode = "200",
      description = "Назначение",
      content = @Content(schema = @Schema(implementation = TicketCategoryAssigneeDto.class))
  )
  @ApiResponse(responseCode = "404", description = "Не найдено")
  @GetMapping("/{id}")
  ResponseEntity<TicketCategoryAssigneeDto> getById(@PathVariable UUID id);

  @Operation(
      summary = "Создать назначение",
      description = """
          Создаёт одно назначение.

          Валидация:
          - если assignmentType=MANAGER -> categoryId должен быть null (иначе 400)
          - если assignmentType=EXECUTOR -> categoryId обязателен (иначе 400)
          """
  )
  @ApiResponse(
      responseCode = "201",
      description = "Создано",
      content = @Content(schema = @Schema(implementation = TicketCategoryAssigneeDto.class))
  )
  @ApiResponse(responseCode = "400", description = "Некорректный запрос")
  @ApiResponse(responseCode = "409", description = "Дубликат (уже существует такое назначение)")
  @PostMapping
  ResponseEntity<TicketCategoryAssigneeDto> create(@RequestBody TicketCategoryAssigneeCreateRequest request);

  @Operation(
      summary = "Изменить назначение",
      description = """
          Обновляет одно назначение по id.

          Валидация:
          - если assignmentType=MANAGER -> categoryId должен быть null
          - если assignmentType=EXECUTOR -> categoryId обязателен
          """
  )
  @ApiResponse(
      responseCode = "200",
      description = "Обновлено",
      content = @Content(schema = @Schema(implementation = TicketCategoryAssigneeDto.class))
  )
  @ApiResponse(responseCode = "400", description = "Некорректный запрос")
  @ApiResponse(responseCode = "404", description = "Не найдено")
  @ApiResponse(responseCode = "409", description = "Конфликт уникальности (после изменения стало дубликатом)")
  @PutMapping("/{id}")
  ResponseEntity<TicketCategoryAssigneeDto> update(
      @PathVariable UUID id,
      @RequestBody TicketCategoryAssigneeUpdateRequest request
  );

  @Operation(summary = "Удалить назначение по id")
  @ApiResponse(responseCode = "204", description = "Удалено")
  @ApiResponse(responseCode = "404", description = "Не найдено")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);
}
