package ru.ryatronth.sd.ticket.domain.comment;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketCommentEntityRepository extends JpaRepository<TicketCommentEntity, UUID> {

  @EntityGraph(attributePaths = "attachments")
  Page<TicketCommentEntity> findAllByTicketIdOrderByCreatedAtAsc(UUID ticketId, Pageable pageable);

}