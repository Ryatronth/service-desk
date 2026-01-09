package ru.ryatronth.sd.department.api.department;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-department",
    url = "${sd.clients.department.url}",
    path = DepartmentApiV1.BASE_PATH
)
public interface DepartmentClientV1 extends DepartmentApiV1 {}
