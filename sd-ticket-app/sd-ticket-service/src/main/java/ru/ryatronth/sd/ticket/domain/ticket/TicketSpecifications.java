package ru.ryatronth.sd.ticket.domain.ticket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.dto.TicketPriority;
import ru.ryatronth.sd.ticket.dto.TicketStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TicketSpecifications {

  public static Specification<TicketEntity> statusEquals(TicketStatus status) {
    return (root, query, cb) -> status == null ? cb.conjunction()
        : cb.equal(root.get("status"), status);
  }

  public static Specification<TicketEntity> priorityEquals(TicketPriority priority) {
    return (root, query, cb) -> priority == null ? cb.conjunction()
        : cb.equal(root.get("priority"), priority);
  }

  public static Specification<TicketEntity> categoryIdEquals(UUID categoryId) {
    return (root, query, cb) -> categoryId == null ? cb.conjunction()
        : cb.equal(root.get("category").get("id"), categoryId);
  }

  public static Specification<TicketEntity> requesterUserIdEquals(UUID userId) {
    return (root, query, cb) -> userId == null ? cb.conjunction()
        : cb.equal(root.get("requesterUserId"), userId);
  }

  public static Specification<TicketEntity> assigneeUserIdEquals(UUID assigneeUserId) {
    return (root, query, cb) -> assigneeUserId == null ? cb.conjunction()
        : cb.equal(root.get("assigneeUserId"), assigneeUserId);
  }

  public static Specification<TicketEntity> dueAtGte(Instant dueFrom) {
    return (root, query, cb) -> dueFrom == null ? cb.conjunction()
        : cb.greaterThanOrEqualTo(root.get("dueAt"), dueFrom);
  }

  public static Specification<TicketEntity> dueAtLte(Instant dueTo) {
    return (root, query, cb) -> dueTo == null ? cb.conjunction()
        : cb.lessThanOrEqualTo(root.get("dueAt"), dueTo);
  }

  public static Specification<TicketEntity> qLike(String q) {
    return (root, query, cb) -> {
      if (isBlank(q)) {
        return cb.conjunction();
      }

      String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";

      return cb.or(
          cb.like(cb.lower(root.get("number")), like),
          cb.like(cb.lower(root.get("title")), like),
          cb.like(cb.lower(root.get("description")), like),

          cb.like(cb.lower(root.get("requesterSnapshot").get("username")), like),
          cb.like(cb.lower(root.get("requesterSnapshot").get("displayName")), like),
          cb.like(cb.lower(root.get("requesterSnapshot").get("email")), like),

          cb.like(cb.lower(root.get("assigneeSnapshot").get("username")), like),
          cb.like(cb.lower(root.get("assigneeSnapshot").get("displayName")), like),
          cb.like(cb.lower(root.get("assigneeSnapshot").get("email")), like)
      );
    };
  }

  @With
  @Builder
  @AllArgsConstructor
  public static class Filter {

    private String q;
    private TicketStatus status;
    private TicketPriority priority;
    private UUID categoryId;
    private UUID requesterUserId;
    private UUID assigneeUserId;
    private Instant dueFrom;
    private Instant dueTo;

    @Builder.Default
    private SearchMode mode = SearchMode.AND;

    public Specification<TicketEntity> toSpec() {
      Specification<TicketEntity> spec = Specification.unrestricted();
      var active = new ArrayList<Specification<TicketEntity>>();

      if (!isBlank(q)) {
        active.add(qLike(q));
      }
      if (status != null) {
        active.add(statusEquals(status));
      }
      if (priority != null) {
        active.add(priorityEquals(priority));
      }
      if (categoryId != null) {
        active.add(categoryIdEquals(categoryId));
      }
      if (requesterUserId != null) {
        active.add(requesterUserIdEquals(requesterUserId));
      }
      if (assigneeUserId != null) {
        active.add(assigneeUserIdEquals(assigneeUserId));
      }
      if (dueFrom != null) {
        active.add(dueAtGte(dueFrom));
      }
      if (dueTo != null) {
        active.add(dueAtLte(dueTo));
      }

      if (active.isEmpty()) {
        return Specification.unrestricted();
      }

      Specification<TicketEntity> combined = active.getFirst();
      for (int i = 1; i < active.size(); i++) {
        combined = (mode == SearchMode.OR)
            ? combined.or(active.get(i))
            : combined.and(active.get(i));
      }

      return spec.and(combined);
    }
  }

  private static boolean isBlank(String s) {
    return s == null || s.isBlank();
  }
}
