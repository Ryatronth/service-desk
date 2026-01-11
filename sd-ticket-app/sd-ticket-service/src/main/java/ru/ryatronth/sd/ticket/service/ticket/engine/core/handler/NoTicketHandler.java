package ru.ryatronth.sd.ticket.service.ticket.engine.core.handler;

import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.NoTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.NoTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.result.HandlerResult;

public interface NoTicketHandler<C extends NoTicketCommand<R>, R> {
  Class<C> supports();

  HandlerResult<R> handle(NoTicketContext ctx, C command);
}
