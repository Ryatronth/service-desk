package ru.ryatronth.sd.ticket.service.ticket.engine.impl.add_comment;

import java.util.UUID;

public record AddTicketCommentEventPayload(
    UUID commentId,
    String comment
) {
}
