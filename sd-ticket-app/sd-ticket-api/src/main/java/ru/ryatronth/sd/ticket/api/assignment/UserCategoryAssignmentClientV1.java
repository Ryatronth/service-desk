package ru.ryatronth.sd.ticket.api.assignment;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-user-category-assigne",
    url = "${sd.clients.ticket.url}",
    path = UserCategoryAssignmentApiV1.BASE_PATH
)
public interface UserCategoryAssignmentClientV1 extends UserCategoryAssignmentApiV1 {
}
