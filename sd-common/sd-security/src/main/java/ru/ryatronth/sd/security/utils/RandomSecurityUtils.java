package ru.ryatronth.sd.security.utils;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.ryatronth.sd.security.dto.CurrentUser;

public class RandomSecurityUtils implements SecurityUtils {

  private static final ThreadLocal<CurrentUser> CTX = ThreadLocal.withInitial(() -> {
    String userId = UUID.randomUUID().toString();
    return new CurrentUser(userId,
        "dev-" + userId.substring(0, 8),
        "dev@" + userId.substring(0, 8) + ".local",
        "89999999999",
        "Dev",
        "User",
        null,
        true,
        "dp-1-1",
        "wp-1-1",
        Set.of("ROLE_USER"));
  });

  @Override
  public Optional<Jwt> currentJwt() {
    return Optional.empty();
  }

  @Override
  public Optional<String> currentUserId() {
    return Optional.ofNullable(CTX.get().userId());
  }

  @Override
  public Optional<CurrentUser> currentUser() {
    return Optional.of(CTX.get());
  }

  @Override
  public CurrentUser currentUserOrThrow() {
    return currentUser().orElseThrow(() -> new IllegalStateException("Текущий пользователь не определён"));
  }

  @Override
  public Set<String> currentRoles() {
    return CTX.get().roles();
  }

}
