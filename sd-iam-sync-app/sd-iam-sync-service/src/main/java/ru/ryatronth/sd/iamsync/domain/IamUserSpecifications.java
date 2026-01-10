package ru.ryatronth.sd.iamsync.domain;

import java.util.ArrayList;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.jpa.domain.Specification;
import ru.ryatronth.sd.iamsync.api.filter.SearchMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IamUserSpecifications {

  public static Specification<IamUserEntity> enabled(Boolean enabled) {
    return (root, query, cb) -> enabled == null ? cb.conjunction() :
        cb.equal(root.get("enabled"), enabled);
  }

  public static Specification<IamUserEntity> usernameEquals(String username) {
    return (root, query, cb) -> isBlank(username) ? cb.conjunction() :
        cb.equal(root.get("username"), username.trim());
  }

  public static Specification<IamUserEntity> emailEquals(String email) {
    return (root, query, cb) -> isBlank(email) ? cb.conjunction() :
        cb.equal(root.get("email"), email.trim());
  }

  public static Specification<IamUserEntity> phoneEquals(String phone) {
    return (root, query, cb) -> isBlank(phone) ? cb.conjunction() :
        cb.equal(root.get("phone"), phone.trim());
  }

  public static Specification<IamUserEntity> departmentIdEquals(String departmentId) {
    return (root, query, cb) -> isBlank(departmentId) ? cb.conjunction() :
        cb.equal(root.get("departmentCode"), departmentId.trim());
  }

  public static Specification<IamUserEntity> workplaceIdEquals(String workplaceId) {
    return (root, query, cb) -> isBlank(workplaceId) ? cb.conjunction() :
        cb.equal(root.get("workplaceCode"), workplaceId.trim());
  }

  public static Specification<IamUserEntity> qLike(String q) {
    return (root, query, cb) -> {
      if (isBlank(q)) {
        return cb.conjunction();
      }

      String like = "%" + q.trim().toLowerCase(Locale.ROOT) + "%";

      return cb.or(
          cb.like(cb.lower(root.get("username")), like),
          cb.like(cb.lower(root.get("email")), like),
          cb.like(cb.lower(root.get("firstName")), like),
          cb.like(cb.lower(root.get("lastName")), like),
          cb.like(cb.lower(root.get("patronymic")), like),
          cb.like(cb.lower(root.get("phone")), like),
          cb.like(cb.lower(root.get("departmentCode")), like),
          cb.like(cb.lower(root.get("workplaceCode")), like));
    };
  }

  @With
  @Builder
  @AllArgsConstructor
  public static class Filter {

    private String q;
    private Boolean enabled;

    private String username;
    private String email;

    private String phone;
    private String departmentCode;
    private String workplaceCode;

    @Builder.Default
    private SearchMode mode = SearchMode.AND;

    public Specification<IamUserEntity> toSpec() {
      Specification<IamUserEntity> spec = Specification.unrestricted();

      var active = new ArrayList<Specification<IamUserEntity>>();

      if (enabled != null) {
        active.add(enabled(enabled));
      }
      if (!isBlank(username)) {
        active.add(usernameEquals(username));
      }
      if (!isBlank(email)) {
        active.add(emailEquals(email));
      }
      if (!isBlank(phone)) {
        active.add(phoneEquals(phone));
      }
      if (!isBlank(departmentCode)) {
        active.add(departmentIdEquals(departmentCode));
      }
      if (!isBlank(workplaceCode)) {
        active.add(workplaceIdEquals(workplaceCode));
      }
      if (!isBlank(q)) {
        active.add(qLike(q));
      }

      if (active.isEmpty()) {
        return spec;
      }

      Specification<IamUserEntity> combined = active.getFirst();
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
