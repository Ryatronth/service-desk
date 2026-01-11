package ru.ryatronth.sd.ticket.service.ticket.engine.impl.add_comment;

import ru.ryatronth.sd.ticket.domain.comment.TicketCommentEntity;

public record AddTicketCommentResult(
    TicketCommentEntity comment
) {
}
