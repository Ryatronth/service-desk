package ru.ryatronth.sd.ticket.api.ticket;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ryatronth.sd.ticket.api.ticket.filter.TicketFilters;
import ru.ryatronth.sd.ticket.dto.ticket.TicketAssigneeChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketCreateRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketDto;
import ru.ryatronth.sd.ticket.dto.ticket.TicketPriorityChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketResolveRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketShortDto;
import ru.ryatronth.sd.ticket.dto.ticket.TicketSlaChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketStatusChangeRequest;
import ru.ryatronth.sd.ticket.dto.ticket.TicketTransferToParenDepartmentRequest;

@Tag(name = "Tickets API", description = "API для работы с обращениями (заявками)")
public interface TicketApiV1 {

  String BASE_PATH = "/api/v1/tickets";

  @Operation(summary = "Создать заявку", responses = {
      @ApiResponse(responseCode = "201", description = "Заявка создана",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PostMapping
  ResponseEntity<TicketDto> create(@Valid @RequestBody TicketCreateRequest request);

  @Operation(summary = "Получить заявку по id", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content)
  })
  @GetMapping("/{id}")
  ResponseEntity<TicketDto> getById(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id
  );

  @Operation(summary = "Получить список заявок", description = "Постраничный список с фильтрами + Pageable")
  @ApiResponse(responseCode = "200", description = "Страница заявок",
      content = @Content(schema = @Schema(implementation = Page.class)))
  @GetMapping
  ResponseEntity<Page<TicketShortDto>> getAll(@ParameterObject TicketFilters filters,
                                              @ParameterObject Pageable pageable);

  @Operation(summary = "Сменить ответственного (исполнителя) заявки", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/assignee")
  ResponseEntity<TicketDto> changeAssignee(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketAssigneeChangeRequest request
  );

  @Operation(summary = "Перевести заявку в родительский филиал и назначить исполнителя", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/transfer-to-parent")
  ResponseEntity<TicketDto> transferToParent(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketTransferToParenDepartmentRequest request
  );

  @Operation(summary = "Сменить статус заявки", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/status")
  ResponseEntity<TicketDto> changeStatus(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketStatusChangeRequest request
  );

  @Operation(summary = "Изменить приоритет с причиной", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/priority")
  ResponseEntity<TicketDto> changePriority(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketPriorityChangeRequest request
  );

  @Operation(summary = "Изменить SLA с причиной", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/sla")
  ResponseEntity<TicketDto> changeSla(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketSlaChangeRequest request
  );

  @Operation(summary = "Завершить заявку", responses = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = TicketDto.class))),
      @ApiResponse(responseCode = "404", description = "Не найдено", content = @Content),
      @ApiResponse(responseCode = "400", description = "Некорректный запрос", content = @Content)
  })
  @PutMapping("/{id}/resolve")
  ResponseEntity<TicketDto> resolve(
      @PathVariable
      @Parameter(in = ParameterIn.PATH, name = "id", required = true, schema = @Schema(format = "uuid"))
      UUID id,
      @Valid @RequestBody TicketResolveRequest request
  );
}
