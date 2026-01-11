package ru.ryatronth.sd.ticket.api.category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ryatronth.sd.ticket.api.category.filters.TicketCategoryFilters;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryCreateRequest;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryDto;
import ru.ryatronth.sd.ticket.dto.category.TicketCategoryUpdateRequest;

@Tag(name = "Ticket Categories", description = "Управление категориями обращений")
public interface TicketCategoryApiV1 {

  String BASE_PATH = "/api/v1/ticket-categories";

  @Operation(summary = "Создать категорию обращения")
  @PostMapping
  ResponseEntity<TicketCategoryDto> create(@RequestBody TicketCategoryCreateRequest request);


  @Operation(summary = "Получить категорию по id")
  @GetMapping("/{id}")
  ResponseEntity<TicketCategoryDto> getById(
      @PathVariable @Parameter(description = "ID категории") UUID id);

  @Operation(summary = "Получить список категорий")
  @GetMapping
  ResponseEntity<Page<TicketCategoryDto>> getAll(@ParameterObject TicketCategoryFilters filters,
                                                 @ParameterObject Pageable pageable);


  @Operation(summary = "Обновить категорию")
  @PutMapping("/{id}")
  ResponseEntity<TicketCategoryDto> update(@PathVariable UUID id,
                                           @RequestBody TicketCategoryUpdateRequest request);

  @Operation(summary = "Удалить категорию")
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);
}
