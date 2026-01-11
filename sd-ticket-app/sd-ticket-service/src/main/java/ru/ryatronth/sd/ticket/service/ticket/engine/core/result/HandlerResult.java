package ru.ryatronth.sd.ticket.service.ticket.engine.core.result;

import java.util.Optional;

public record HandlerResult<R>(
    R response,
    Optional<TicketHistoryEvent> event
) {

  public static <R> HandlerResult<R> of(R response, TicketHistoryEvent event) {
    return new HandlerResult<>(response, Optional.ofNullable(event));
  }

  public static <R> HandlerResult<R> of(R response) {
    return new HandlerResult<>(response, Optional.empty());
  }

}

