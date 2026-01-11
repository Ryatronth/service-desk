package ru.ryatronth.sd.ticket.service.ticket.engine.core;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ryatronth.sd.error.exception.NotFoundException;
import ru.ryatronth.sd.security.dto.CurrentUser;
import ru.ryatronth.sd.security.utils.SecurityUtils;
import ru.ryatronth.sd.ticket.domain.ticket.TicketEntityRepository;
import ru.ryatronth.sd.ticket.service.event.TicketEventWriter;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.NoTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.command.OnTicketCommand;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.NoTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.context.OnTicketContext;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.NoTicketHandler;
import ru.ryatronth.sd.ticket.service.ticket.engine.core.handler.OnTicketHandler;

@Service
public class TicketEngine {

  private final SecurityUtils securityUtils;
  private final TicketEntityRepository ticketRepo;
  private final TicketEventWriter eventWriter;

  private final Map<Class<?>, NoTicketHandler<?, ?>> createHandlers;
  private final Map<Class<?>, OnTicketHandler<?, ?>> onTicketHandlers;

  public TicketEngine(SecurityUtils securityUtils,
                      TicketEntityRepository ticketRepo,
                      TicketEventWriter eventWriter,
                      List<NoTicketHandler<?, ?>> createHandlers,
                      List<OnTicketHandler<?, ?>> onTicketHandlers) {
    this.securityUtils = securityUtils;
    this.ticketRepo = ticketRepo;
    this.eventWriter = eventWriter;

    this.createHandlers = createHandlers.stream()
        .collect(Collectors.toMap(NoTicketHandler::supports, h -> h));

    this.onTicketHandlers = onTicketHandlers.stream()
        .collect(Collectors.toMap(OnTicketHandler::supports, h -> h));
  }

  @SuppressWarnings("unchecked")
  private <C extends NoTicketCommand<R>, R> NoTicketHandler<C, R> createHandlerFor(C cmd) {
    var h = (NoTicketHandler<C, R>) createHandlers.get(cmd.getClass());
    if (h == null) {
      throw new IllegalStateException("No create handler for " + cmd.getClass().getName());
    }
    return h;
  }

  @SuppressWarnings("unchecked")
  private <C extends OnTicketCommand<R>, R> OnTicketHandler<C, R> onTicketHandlerFor(C cmd) {
    var h = (OnTicketHandler<C, R>) onTicketHandlers.get(cmd.getClass());
    if (h == null) {
      throw new IllegalStateException("No handler for " + cmd.getClass().getName());
    }
    return h;
  }

  @Transactional
  public <R, C extends NoTicketCommand<R>> R executeNoTicket(C command) {
    var actor = securityUtils.currentUserOrThrow();
    var ctx = new NoTicketContext(actor);

    var handler = createHandlerFor(command);
    var result = handler.handle(ctx, command);

    result.event().ifPresent(ev ->
        eventWriter.write(ctx.actor(), ctx.ticketOrThrow(), ev.type(), ev.payload(), ev.comment())
    );

    return result.response();
  }

  @Transactional
  public <R, C extends OnTicketCommand<R>> R executeOnTicket(C command) {
    var actor = securityUtils.currentUserOrThrow();
    var ticket = ticketRepo.findById(command.ticketId())
        .orElseThrow(() -> new NotFoundException("Заявка не найдена: " + command.ticketId()));

    var ctx = new OnTicketContext(actor, ticket);

    var handler = onTicketHandlerFor(command);
    var result = handler.handle(ctx, command);

    result.event().ifPresent(ev ->
        eventWriter.write(ctx.actor(), ctx.ticketOrThrow(), ev.type(), ev.payload(), ev.comment())
    );

    return result.response();
  }

  @Transactional
  public <R, C extends OnTicketCommand<R>> R executeOnTicketAs(CurrentUser actor, C command) {
    var ticket = ticketRepo.findById(command.ticketId())
        .orElseThrow(() -> new NotFoundException("Заявка не найдена: " + command.ticketId()));

    var ctx = new OnTicketContext(actor, ticket);

    var handler = onTicketHandlerFor(command);
    var result = handler.handle(ctx, command);

    result.event().ifPresent(ev ->
        eventWriter.write(ctx.actor(), ctx.ticketOrThrow(), ev.type(), ev.payload(), ev.comment())
    );

    return result.response();
  }

}

