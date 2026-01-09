package ru.ryatronth.sd.department.domain.code;

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
public final class DepartmentCodeSpecifications {

    public static Specification<DepartmentCodeEntity> codeEquals(String code) {
        return (root, query, cb) -> isBlank(code) ? cb.conjunction() : cb.equal(cb.lower(root.get("code")), code.trim().toLowerCase(Locale.ROOT));
    }

    public static Specification<DepartmentCodeEntity> qLike(String q) {
        return (root, query, cb) -> {
            if (isBlank(q)) return cb.conjunction();
            String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";
            return cb.like(cb.lower(root.get("code")), like);
        };
    }

    @With
    @Builder
    @AllArgsConstructor
    public static class Filter {

        private String q;
        private String code;

        @Builder.Default
        private SearchMode mode = SearchMode.AND;

        public Specification<DepartmentCodeEntity> toSpec() {
            Specification<DepartmentCodeEntity> spec = Specification.unrestricted();

            var active = new ArrayList<Specification<DepartmentCodeEntity>>();

            if (!isBlank(code)) active.add(codeEquals(code));
            if (!isBlank(q)) active.add(qLike(q));

            if (active.isEmpty()) {
                return Specification.unrestricted();
            }

            Specification<DepartmentCodeEntity> combined = active.getFirst();
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
