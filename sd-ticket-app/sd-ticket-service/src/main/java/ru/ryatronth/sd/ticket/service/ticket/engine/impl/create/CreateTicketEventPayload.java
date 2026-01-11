package ru.ryatronth.sd.ticket.service.ticket.engine.impl.create;

public record CreateTicketEventPayload(
    String ticketNumber,
    String ticketTitle
) {
}
