package ru.ryatronth.sd.iamsync.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-iam-sync-users",
    url = "${sd.clients.iam-sync.url}",
    path = IamUsersApiV1.BASE_PATH
)
public interface IamUsersClientV1 extends IamUsersApiV1 {
}
