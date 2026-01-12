package ru.ryatronth.sd.ticket.domain.assignment;

import java.util.ArrayList;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.ticket.api.common.SearchMode;
import ru.ryatronth.sd.ticket.dto.assignment.TicketAssignmentType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TicketCategoryAssigneeSpecifications {

  public static Specification<TicketCategoryAssigneeEntity> departmentIdEquals(UUID departmentId) {
    return (root, query, cb) -> departmentId == null ? cb.conjunction()
        : cb.equal(root.get("departmentId"), departmentId);
  }

  public static Specification<TicketCategoryAssigneeEntity> userIdEquals(UUID userId) {
    return (root, query, cb) -> userId == null ? cb.conjunction()
        : cb.equal(root.get("userId"), userId);
  }

  public static Specification<TicketCategoryAssigneeEntity> assignmentTypeEquals(TicketAssignmentType type) {
    return (root, query, cb) -> type == null ? cb.conjunction()
        : cb.equal(root.get("assignmentType"), type);
  }

  public static Specification<TicketCategoryAssigneeEntity> categoryIdEquals(UUID categoryId) {
    return (root, query, cb) -> categoryId == null ? cb.conjunction()
        : cb.equal(root.get("category").get("id"), categoryId);
  }

  public static Specification<TicketCategoryAssigneeEntity> categoryIsNull() {
    return (root, query, cb) -> cb.isNull(root.get("category"));
  }

  @With
  @Builder
  @AllArgsConstructor
  public static class Filter {

    private UUID departmentId;
    private UUID categoryId;
    private UUID userId;
    private TicketAssignmentType assignmentType;

    @Builder.Default
    private SearchMode mode = SearchMode.AND;

    public Specification<TicketCategoryAssigneeEntity> toSpec() {
      Specification<TicketCategoryAssigneeEntity> spec = Specification.unrestricted();

      var active = new ArrayList<Specification<TicketCategoryAssigneeEntity>>();

      if (departmentId != null) {
        active.add(departmentIdEquals(departmentId));
      }
      if (categoryId != null) {
        active.add(categoryIdEquals(categoryId));
      }
      if (userId != null) {
        active.add(userIdEquals(userId));
      }
      if (assignmentType != null) {
        active.add(assignmentTypeEquals(assignmentType));

        if (assignmentType == TicketAssignmentType.MANAGER) {
          active.add(categoryIsNull());
        }
      }

      if (active.isEmpty()) {
        return spec;
      }

      Specification<TicketCategoryAssigneeEntity> combined = active.getFirst();
      for (int i = 1; i < active.size(); i++) {
        combined = (mode == SearchMode.OR)
            ? combined.or(active.get(i))
            : combined.and(active.get(i));
      }

      return spec.and(combined);
    }
  }
}
