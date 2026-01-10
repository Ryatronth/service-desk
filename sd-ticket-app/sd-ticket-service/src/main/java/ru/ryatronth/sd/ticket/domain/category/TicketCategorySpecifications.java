package ru.ryatronth.sd.ticket.domain.category;

import java.util.ArrayList;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.dto.TicketPriority;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TicketCategorySpecifications {

  public static Specification<TicketCategoryEntity> priorityEquals(TicketPriority priority) {
    return (root, query, cb) -> priority == null ? cb.conjunction() :
        cb.equal(root.get("priority"), priority);
  }

  public static Specification<TicketCategoryEntity> qLike(String q) {
    return (root, query, cb) -> {
      if (isBlank(q)) {
        return cb.conjunction();
      }
      String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
      return cb.or(
          cb.like(cb.lower(root.get("name")), like),
          cb.like(cb.lower(root.get("description")), like)
      );
    };
  }

  @With
  @Builder
  @AllArgsConstructor
  public static class Filter {

    private String q;
    private TicketPriority priority;

    @Builder.Default
    private SearchMode mode = SearchMode.AND;

    public Specification<TicketCategoryEntity> toSpec() {
      Specification<TicketCategoryEntity> spec = Specification.unrestricted();

      var active = new ArrayList<Specification<TicketCategoryEntity>>();

      if (!isBlank(q)) {
        active.add(qLike(q));
      }
      if (priority != null) {
        active.add(priorityEquals(priority));
      }

      if (active.isEmpty()) {
        return Specification.unrestricted();
      }

      Specification<TicketCategoryEntity> combined = active.getFirst();
      for (int i = 1; i < active.size(); i++) {
        combined =
            (mode == SearchMode.OR) ? combined.or(active.get(i)) : combined.and(active.get(i));
      }

      return spec.and(combined);
    }
  }

  private static boolean isBlank(String s) {
    return s == null || s.isBlank();
  }
}
