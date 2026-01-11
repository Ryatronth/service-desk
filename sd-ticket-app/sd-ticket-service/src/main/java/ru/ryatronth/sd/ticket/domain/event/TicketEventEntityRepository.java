package ru.ryatronth.sd.ticket.domain.event;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TicketEventEntityRepository extends JpaRepository<TicketEventEntity, UUID>,
    JpaSpecificationExecutor<TicketEventEntity> {

  Page<TicketEventEntity> findAllByTicketIdOrderByCreatedAtAsc(UUID ticketId, Pageable pageable);

}