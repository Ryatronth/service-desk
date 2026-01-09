package ru.ryatronth.sd.department.domain.department;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.department.api.common.SearchMode;

import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DepartmentSpecifications {

    public static Specification<DepartmentEntity> parentIdEquals(UUID parentId) {
        return (root, query, cb) -> parentId == null ? cb.conjunction() : cb.equal(root.get("parent").get("id"), parentId);
    }

    public static Specification<DepartmentEntity> codeIdEquals(UUID codeId) {
        return (root, query, cb) -> codeId == null ? cb.conjunction() : cb.equal(root.get("code").get("id"), codeId);
    }

    public static Specification<DepartmentEntity> typeIdEquals(UUID typeId) {
        return (root, query, cb) -> typeId == null ? cb.conjunction() : cb.equal(root.get("type").get("id"), typeId);
    }

    public static Specification<DepartmentEntity> qLike(String q) {
        return (root, query, cb) -> {
            if (isBlank(q)) {
                return cb.conjunction();
            }

            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";

            return cb.or(cb.like(cb.lower(root.get("name")), like),
                         cb.like(cb.lower(root.get("area")), like),
                         cb.like(cb.lower(root.get("address")), like),
                         cb.like(cb.lower(root.get("code").get("code")), like),
                         cb.like(cb.lower(root.get("type").get("name")), like));
        };
    }

    @With
    @Builder
    @AllArgsConstructor
    public static class Filter {

        private String q;

        private UUID parentId;

        private UUID codeId;

        private UUID typeId;

        @Builder.Default
        private SearchMode mode = SearchMode.AND;

        public Specification<DepartmentEntity> toSpec() {
            Specification<DepartmentEntity> spec = Specification.unrestricted();

            var active = new ArrayList<Specification<DepartmentEntity>>();

            if (parentId != null) {
                active.add(parentIdEquals(parentId));
            }
            if (codeId != null) {
                active.add(codeIdEquals(codeId));
            }
            if (typeId != null) {
                active.add(typeIdEquals(typeId));
            }
            if (!isBlank(q)) {
                active.add(qLike(q));
            }

            if (active.isEmpty()) {
                return Specification.unrestricted();
            }

            Specification<DepartmentEntity> combined = active.getFirst();
            for (int i = 1; i < active.size(); i++) {
                combined = (mode == SearchMode.OR) ? combined.or(active.get(i)) : combined.and(active.get(i));
            }

            return spec.and(combined);
        }

    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

}
