package ru.ryatronth.sd.ticket.api.category;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-ticket-category",
    url = "${sd.clients.ticket.url}",
    path = TicketCategoryApiV1.BASE_PATH
)
public interface TicketCategoryClientV1 extends TicketCategoryApiV1 {
}
