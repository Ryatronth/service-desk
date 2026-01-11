package ru.ryatronth.sd.ticket.api.event;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-ticket-event",
    url = "${sd.clients.ticket.url}",
    path = TicketEventApiV1.BASE_PATH
)
public interface TicketEventClientV1 extends TicketEventApiV1 {
}
