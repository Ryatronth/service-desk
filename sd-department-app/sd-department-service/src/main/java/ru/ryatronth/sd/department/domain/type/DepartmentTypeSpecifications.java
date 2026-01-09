package ru.ryatronth.sd.department.domain.type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.department.api.common.SearchMode;

import java.util.ArrayList;
import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DepartmentTypeSpecifications {

    public static Specification<DepartmentTypeEntity> qLike(String q) {
        return (root, query, cb) -> {
            if (isBlank(q)) {
                return cb.conjunction();
            }
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            return cb.like(cb.lower(root.get("name")), like);
        };
    }

    @With
    @Builder
    @AllArgsConstructor
    public static class Filter {

        private String q;

        @Builder.Default
        private SearchMode mode = SearchMode.AND;

        public Specification<DepartmentTypeEntity> toSpec() {
            Specification<DepartmentTypeEntity> spec = Specification.unrestricted();

            var active = new ArrayList<Specification<DepartmentTypeEntity>>();

            if (!isBlank(q)) {
                active.add(qLike(q));
            }

            if (active.isEmpty()) {
                return Specification.unrestricted();
            }

            Specification<DepartmentTypeEntity> combined = active.getFirst();
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
