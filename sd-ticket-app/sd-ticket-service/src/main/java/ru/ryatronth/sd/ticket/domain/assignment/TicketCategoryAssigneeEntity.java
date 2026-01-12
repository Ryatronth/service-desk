package ru.ryatronth.sd.ticket.domain.assignment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.ryatronth.sd.ticket.domain.category.TicketCategoryEntity;
import ru.ryatronth.sd.ticket.dto.assignment.TicketAssignmentType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "ticket_category_assignee",
    indexes = {
        @Index(name = "idx_tca_category_department", columnList = "category_id, department_id"),
        @Index(name = "idx_tca_user_department", columnList = "user_id, department_id")
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_tca_department_user_category", columnNames = {"department_id", "user_id", "category_id"})
    }
)
public class TicketCategoryAssigneeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_tca_category"))
  private TicketCategoryEntity category;

  @Enumerated(EnumType.STRING)
  @Column(name = "assignment_type", nullable = false)
  private TicketAssignmentType assignmentType;

  @Column(name = "department_id", nullable = false)
  private UUID departmentId;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}

