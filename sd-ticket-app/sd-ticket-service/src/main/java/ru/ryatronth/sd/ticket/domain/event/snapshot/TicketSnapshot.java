package ru.ryatronth.sd.ticket.domain.event.snapshot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.CategorySnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.DepartmentSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.WorkplaceSnapshot;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TicketStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketSnapshot {

  private UUID id;

  private String number;

  private String title;

  private String description;

  private UUID categoryId;

  private CategorySnapshot categorySnapshot;

  private TicketPriority priority;

  private Instant dueAt;

  private TicketStatus status;

  private UUID requesterUserId;

  private UserSnapshot requesterSnapshot;

  private UUID requesterDepartmentId;

  private DepartmentSnapshot requesterDepartmentSnapshot;

  private UUID currentDepartmentId;

  private DepartmentSnapshot currentDepartmentSnapshot;

  private WorkplaceSnapshot requesterWorkplaceSnapshot;

  private UUID assigneeUserId;

  private UserSnapshot assigneeSnapshot;

  private Instant closedAt;

  private Long spentTimeMinutes;

  private BigDecimal spentMoney;

}
