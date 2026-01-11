package ru.ryatronth.sd.ticket.service.ticket.engine.core.context;

import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public final class OnTicketContext implements TicketCommandContext {

  private final CurrentUser actor;
  private final TicketEntity ticket;

  public OnTicketContext(CurrentUser actor, TicketEntity ticket) {
    if (ticket == null) {
      throw new IllegalArgumentException("ticket is null");
    }
    this.actor = actor;
    this.ticket = ticket;
  }

  @Override
  public CurrentUser actor() {
    return actor;
  }

  @Override
  public TicketEntity ticketOrThrow() {
    return ticket;
  }
}

