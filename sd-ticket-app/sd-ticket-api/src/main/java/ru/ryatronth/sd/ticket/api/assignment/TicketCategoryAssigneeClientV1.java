package ru.ryatronth.sd.ticket.api.assignment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-ticket-category-assignee",
    url = "${sd.clients.ticket.url}",
    path = TicketCategoryAssigneeApiV1.BASE_PATH
)
public interface TicketCategoryAssigneeClientV1 {
}
