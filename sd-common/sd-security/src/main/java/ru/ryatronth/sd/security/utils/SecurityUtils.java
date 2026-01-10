package ru.ryatronth.sd.security.utils;

import java.util.Optional;
import java.util.Set;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.ryatronth.sd.security.dto.CurrentUser;

public interface SecurityUtils {

  Optional<Jwt> currentJwt();

  Optional<String> currentUserId();

  Optional<CurrentUser> currentUser();

  Set<String> currentRoles();
}
