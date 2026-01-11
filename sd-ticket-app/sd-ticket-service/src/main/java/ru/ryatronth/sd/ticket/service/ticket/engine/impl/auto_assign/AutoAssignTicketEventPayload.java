package ru.ryatronth.sd.ticket.service.ticket.engine.impl.auto_assign;

import java.util.UUID;

public record AutoAssignTicketEventPayload(
    UUID assigneeUserId
) {
}
