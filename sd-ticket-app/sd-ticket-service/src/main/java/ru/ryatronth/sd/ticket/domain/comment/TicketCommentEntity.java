package ru.ryatronth.sd.ticket.domain.comment;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
import ru.ryatronth.sd.ticket.domain.attachment.TicketAttachmentEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;
import ru.ryatronth.sd.ticket.domain.ticket.snapshot.UserSnapshot;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "ticket_comment",
    indexes = {
        @Index(name = "idx_ticket_comment_ticket", columnList = "ticket_id"),
        @Index(name = "idx_ticket_comment_created_at", columnList = "created_at")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketCommentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "ticket_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_ticket_comment_ticket")
  )
  private TicketEntity ticket;

  @Column(name = "author_user_id", nullable = false)
  private UUID authorUserId;

  @Type(JsonType.class)
  @Column(name = "author_snapshot", columnDefinition = "jsonb", nullable = false)
  private UserSnapshot authorSnapshot;

  @Column(name = "body", nullable = false, columnDefinition = "text")
  private String body;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<TicketAttachmentEntity> attachments = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
