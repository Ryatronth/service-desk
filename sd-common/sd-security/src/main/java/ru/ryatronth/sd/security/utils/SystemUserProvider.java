package ru.ryatronth.sd.security.utils;

import java.util.Set;
import ru.ryatronth.sd.security.dto.CurrentUser;

public class SystemUserProvider {

  public CurrentUser systemActor() {
    String userId = "09d6d8da-ed9e-40f0-92a3-214dfd8279c0";
    return new CurrentUser(userId,
        "system-" + userId.substring(0, 8),
        "system@" + userId.substring(0, 8) + ".local",
        "99999999999",
        "system",
        "system",
        "system",
        true,
        "system",
        "system",
        Set.of());
  }

}
