package ru.ryatronth.sd.ticket.domain.ticket;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TicketEntityRepository extends JpaRepository<TicketEntity, UUID>,
    JpaSpecificationExecutor<TicketEntity> {

  @Query("""
      select t.id
      from TicketEntity t
      where t.assigneeUserId is null
        and (t.lastAssigneeAttempt is null or t.lastAssigneeAttempt <= :due)
      order by t.createdAt asc
      """)
  List<UUID> findUnassigned(@Param("due") Instant due);

}