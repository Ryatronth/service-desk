package ru.ryatronth.sd.ticket.api.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentCreateRequest;
import ru.ryatronth.sd.ticket.dto.comment.TicketCommentDto;

@Tag(name = "Ticket Comments API", description = "Комментарии к заявкам")
public interface TicketCommentApiV1 {

  String BASE_PATH = "/api/v1/tickets/{ticketId}/comments";

  @Operation(summary = "Получить комментарии заявки")
  @ApiResponse(responseCode = "200", description = "OK",
      content = @Content(schema = @Schema(implementation = TicketCommentDto.class)))
  @GetMapping
  ResponseEntity<List<TicketCommentDto>> getAll(@PathVariable UUID ticketId, @ParameterObject Pageable pageable);

  @Operation(summary = "Добавить комментарий")
  @ApiResponse(responseCode = "201", description = "Создано",
      content = @Content(schema = @Schema(implementation = TicketCommentDto.class)))
  @PostMapping
  ResponseEntity<TicketCommentDto> add(
      @PathVariable UUID ticketId,
      @RequestBody TicketCommentCreateRequest request
  );
}
