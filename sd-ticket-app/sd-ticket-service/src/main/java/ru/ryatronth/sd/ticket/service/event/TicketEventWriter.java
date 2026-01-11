package ru.ryatronth.sd.ticket.service.event;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntity;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntityRepository;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;
import ru.ryatronth.sd.ticket.mapper.ticket.TicketSnapshotMapper;

@Service
@RequiredArgsConstructor
public class TicketEventWriter {

  private final TicketEventEntityRepository eventRepository;

  private final TicketSnapshotMapper ticketSnapshotMapper;

  @Transactional
  public void write(CurrentUser actor, TicketEntity ticket, TicketEventType type, Object payload, String comment) {
    var ev = new TicketEventEntity();

    ev.setTicket(ticket);
    ev.setTicketSnapshot(ticketSnapshotMapper.toDomainSnapshot(ticket));
    ev.setType(type);

    ev.setActorUserId(actor.userId() == null ? null : UUID.fromString(actor.userId()));
    ev.setActorSnapshot(UserSnapshot.builder()
        .username(actor.username())
        .displayName(actor.buildFullName())
        .email(actor.email())
        .build());

    ev.setPayload(payload);
    ev.setComment(comment);

    eventRepository.save(ev);
  }
}
