package ru.ryatronth.sd.ticket.domain.ticket.number;

import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Component;

@Component
public class UlidTicketNumberGenerator implements TicketNumberGenerator {

  @Override
  public String generate() {
    return "TCK-" + UlidCreator.getUlid().toString();
  }

}
