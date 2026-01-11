package ru.ryatronth.sd.ticket.api.comment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-ticket-comment",
    url = "${sd.clients.ticket.url}",
    path = TicketCommentApiV1.BASE_PATH
)
public interface TicketCommentClientV1 extends TicketCommentApiV1 {
}
