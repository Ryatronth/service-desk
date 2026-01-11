package ru.ryatronth.sd.ticket.controller.event;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ryatronth.sd.ticket.api.event.TicketEventApiV1;
import ru.ryatronth.sd.ticket.dto.event.TicketEventDto;
import ru.ryatronth.sd.ticket.mapper.event.TicketEventMapper;
import ru.ryatronth.sd.ticket.service.event.TicketEventService;

@RestController
@RequestMapping(TicketEventApiV1.BASE_PATH)
@RequiredArgsConstructor
public class TicketEventController implements TicketEventApiV1 {

  private final TicketEventService ticketEventService;
  private final TicketEventMapper ticketEventMapper;

  @Override
  public ResponseEntity<List<TicketEventDto>> getAll(UUID ticketId, Pageable pageable) {
    var list = ticketEventService.findAll(ticketId, pageable)
        .stream()
        .map(ticketEventMapper::toDto)
        .toList();

    return ResponseEntity.ok(list);
  }
}
