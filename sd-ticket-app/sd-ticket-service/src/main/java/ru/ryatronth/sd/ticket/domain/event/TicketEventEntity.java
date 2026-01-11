package ru.ryatronth.sd.ticket.domain.event;

import com.vladmihalcea.hibernate.type.json.JsonType;
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
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import ru.ryatronth.sd.ticket.domain.event.snapshot.TicketSnapshot;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;
import ru.ryatronth.sd.ticket.dto.event.TicketEventType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "ticket_event",
    indexes = {
        @Index(name = "idx_ticket_event_ticket", columnList = "ticket_id"),
        @Index(name = "idx_ticket_event_created_at", columnList = "created_at"),
        @Index(name = "idx_ticket_event_type", columnList = "type")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketEventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "ticket_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_ticket_event_ticket")
  )
  private TicketEntity ticket;

  @Type(JsonType.class)
  @Column(name = "ticket_snapshot", columnDefinition = "jsonb", nullable = false)
  private TicketSnapshot ticketSnapshot;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", nullable = false, length = 64)
  private TicketEventType type;

  @Column(name = "actor_user_id", nullable = false)
  private UUID actorUserId;

  @Type(JsonType.class)
  @Column(name = "actor_snapshot", columnDefinition = "jsonb", nullable = false)
  private UserSnapshot actorSnapshot;

  @Type(JsonType.class)
  @Column(name = "payload", columnDefinition = "jsonb")
  private Object payload;

  @Column(name = "comment", columnDefinition = "text")
  private String comment;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
