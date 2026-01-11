package ru.ryatronth.sd.ticket.service.ticket.engine.core.context;

import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntity;

public sealed interface TicketCommandContext permits NoTicketContext, OnTicketContext {
  CurrentUser actor();

  TicketEntity ticketOrThrow();
}

