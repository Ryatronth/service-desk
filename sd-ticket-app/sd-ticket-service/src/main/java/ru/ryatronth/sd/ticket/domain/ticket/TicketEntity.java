package ru.ryatronth.sd.ticket.domain.ticket;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import ru.ryatronth.sd.ticket.domain.attachment.TicketAttachmentEntity;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntity;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;
import ru.ryatronth.sd.ticket.domain.event.TicketEventEntity;
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
@Entity
@Table(
    name = "ticket",
    indexes = {
        @Index(name = "idx_ticket_status", columnList = "status"),
        @Index(name = "idx_ticket_priority", columnList = "priority"),
        @Index(name = "idx_ticket_department", columnList = "requester_department_id"),
        @Index(name = "idx_ticket_assignee", columnList = "assignee_user_id"),
        @Index(name = "idx_ticket_due_at", columnList = "due_at")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_ticket_number", columnNames = "number")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @Column(name = "number", nullable = false, length = 64, unique = true)
  private String number;

  @Column(name = "title", nullable = false, length = 512)
  private String title;

  @Column(name = "description", nullable = false, columnDefinition = "text")
  private String description;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private TicketCategoryEntity category;

  @Type(JsonType.class)
  @Column(name = "category_snapshot", columnDefinition = "jsonb", nullable = false)
  private CategorySnapshot categorySnapshot;

  @Enumerated(EnumType.STRING)
  @Column(name = "priority", nullable = false, length = 32)
  private TicketPriority priority;

  @Column(name = "due_at")
  private Instant dueAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 32)
  private TicketStatus status;

  @Column(name = "requester_user_id", nullable = false)
  private UUID requesterUserId;

  @Type(JsonType.class)
  @Column(name = "requester_snapshot", columnDefinition = "jsonb", nullable = false)
  private UserSnapshot requesterSnapshot;

  @Column(name = "requester_department_id", nullable = false)
  private UUID requesterDepartmentId;

  @Type(JsonType.class)
  @Column(name = "requester_department_snapshot", columnDefinition = "jsonb", nullable = false)
  private DepartmentSnapshot requesterDepartmentSnapshot;

  @Column(name = "current_department_id", nullable = false)
  private UUID currentDepartmentId;

  @Type(JsonType.class)
  @Column(name = "current_department_snapshot", columnDefinition = "jsonb", nullable = false)
  private DepartmentSnapshot currentDepartmentSnapshot;

  @Type(JsonType.class)
  @Column(name = "requester_workplace_snapshot", columnDefinition = "jsonb", nullable = false)
  private WorkplaceSnapshot requesterWorkplaceSnapshot;

  @Column(name = "assignee_user_id")
  private UUID assigneeUserId;

  @Type(JsonType.class)
  @Column(name = "assignee_snapshot", columnDefinition = "jsonb")
  private UserSnapshot assigneeSnapshot;

  @Column(name = "closed_at")
  private Instant closedAt;

  @Column(name = "spent_time_minutes")
  private Long spentTimeMinutes;

  @Column(name = "spent_money", columnDefinition = "numeric")
  private BigDecimal spentMoney;

  @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<TicketCommentEntity> comments = new ArrayList<>();

  @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<TicketAttachmentEntity> attachments = new ArrayList<>();

  @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<TicketEventEntity> events = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
}
