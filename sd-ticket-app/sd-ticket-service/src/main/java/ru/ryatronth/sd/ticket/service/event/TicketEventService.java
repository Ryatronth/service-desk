package ru.ryatronth.sd.ticket.service.event;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntity;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntityRepository;

@Service
@RequiredArgsConstructor
public class TicketEventService {

  private final TicketEventEntityRepository eventRepository;

  @Transactional(readOnly = true)
  public Page<TicketEventEntity> findAll(UUID ticketId, Pageable pageable) {
    return eventRepository.findAllByTicketIdOrderByCreatedAtAsc(ticketId, pageable);
  }

}
