package ru.ryatronth.sd.ticket.api.ticket;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-ticket",
    url = "${sd.clients.ticket.url}",
    path = TicketApiV1.BASE_PATH
)
public interface TicketClientV1 extends TicketApiV1 {
}
