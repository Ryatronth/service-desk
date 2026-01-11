package ru.ryatronth.sd.ticket.domain.attachment;

import com.vladmihalcea.hibernate.type.json.JsonType;
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
import ru.ryatronth.sd.ticket.domain.attachment.snapshot.FileSnapshot;
import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "ticket_attachment",
    indexes = {
        @Index(name = "idx_ticket_attachment_ticket", columnList = "ticket_id"),
        @Index(name = "idx_ticket_attachment_comment", columnList = "comment_id"),
        @Index(name = "idx_ticket_attachment_file", columnList = "file_id")
    }
)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TicketAttachmentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  @EqualsAndHashCode.Include
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "ticket_id",
      nullable = false,
      foreignKey = @ForeignKey(name = "fk_ticket_attachment_ticket")
  )
  private TicketEntity ticket;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "comment_id",
      foreignKey = @ForeignKey(name = "fk_ticket_attachment_comment")
  )
  private TicketCommentEntity comment;

  @Column(name = "file_id", nullable = false)
  private UUID fileId;

  @Type(JsonType.class)
  @Column(name = "file_snapshot", columnDefinition = "jsonb", nullable = false)
  private FileSnapshot fileSnapshot;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
}
