package ru.ryatronth.sd.ticket.api.event;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.ryatronth.sd.ticket.dto.event.TicketEventDto;

@Tag(name = "Ticket Events API", description = "История/события заявки (таймлайн)")
public interface TicketEventApiV1 {

  String BASE_PATH = "/api/v1/tickets/{ticketId}/events";

  @Operation(summary = "Получить события заявки")
  @GetMapping
  ResponseEntity<List<TicketEventDto>> getAll(@PathVariable UUID ticketId, @ParameterObject Pageable pageable);
}
