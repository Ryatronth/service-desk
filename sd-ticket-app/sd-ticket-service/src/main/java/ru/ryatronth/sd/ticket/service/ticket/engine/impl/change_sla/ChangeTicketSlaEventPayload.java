package ru.ryatronth.sd.ticket.service.ticket.engine.impl.change_sla;

import java.time.Instant;

public record ChangeTicketSlaEventPayload(
    Instant oldDue,
    Instant newDue
) {
}
