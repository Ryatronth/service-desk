package ru.ryatronth.sd.ticket.service.ticket.engine.impl.auto_assign;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.security.utils.SystemUserProvider;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.dto.TicketStatus;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.TicketEngine;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketAutoAssignScheduler {

  private final TicketEntityRepository ticketRepo;
  private final TicketEngine ticketEngine;
  private final SystemUserProvider systemUserProvider;

  private static final Duration MIN_AGE = Duration.ofSeconds(5);

  @Scheduled(fixedDelayString = "${sd.ticket.auto-assign.fixed-delay-ms:30000}")
  @Transactional
  public void autoAssign() {
    var before = Instant.now().minus(MIN_AGE);

    var ids = ticketRepo.findUnassigned(TicketStatus.NEW, before);
    if (ids.isEmpty()) {
      return;
    }

    var actor = systemUserProvider.systemActor();

    for (UUID ticketId : ids) {
      try {
        ticketEngine.executeOnTicketAs(actor, new AutoAssignTicketCommand(ticketId));
      } catch (Exception e) {
        log.warn("Auto-assign failed for ticketId={}: {}", ticketId, e.getMessage(), e);
      }
    }
  }

}
