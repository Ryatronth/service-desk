package ru.ryatronth.sd.ticket.util;

import java.time.Instant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TicketUtil {

  public static Instant calcDueAt(Instant base, Long duration) {
    if (base == null || duration == null) {
      return null;
    }
    return base.plusSeconds(duration * 60);
  }

}
