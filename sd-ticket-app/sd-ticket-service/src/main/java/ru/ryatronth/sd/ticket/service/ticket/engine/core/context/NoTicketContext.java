package ru.ryatronth.sd.ticket.service.ticket.engine.core.context;

import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public final class NoTicketContext implements TicketCommandContext {

  private final CurrentUser actor;

  private TicketEntity ticket;

  public NoTicketContext(CurrentUser actor) {
    this.actor = actor;
  }

  @Override
  public CurrentUser actor() {
    return actor;
  }

  public void bindTicket(TicketEntity ticket) {
    if (ticket == null) {
      throw new IllegalArgumentException("ticket is null");
    }
    this.ticket = ticket;
  }

  @Override
  public TicketEntity ticketOrThrow() {
    if (ticket == null) {
      throw new IllegalStateException("Create handler did not bind ticket to context");
    }
    return ticket;
  }

}
