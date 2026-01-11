package ru.ryatronth.sd.ticket.service.ticket.engine.core.command;

import java.util.UUID;

public interface OnTicketCommand<R> {
  UUID ticketId();
}
