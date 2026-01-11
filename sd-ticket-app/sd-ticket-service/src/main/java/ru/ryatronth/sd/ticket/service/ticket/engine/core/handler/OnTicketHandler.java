package ru.ryatronth.sd.ticket.service.ticket.engine.core.handler;

import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;

public interface OnTicketHandler<C extends OnTicketCommand<R>, R> {
  Class<C> supports();

  HandlerResult<R> handle(OnTicketContext ctx, C command);
}
