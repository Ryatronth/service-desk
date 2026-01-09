package ru.ryatronth.sd.department.api.code;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-department-code",
    url = "${sd.clients.department.url}",
    path = DepartmentCodeApiV1.BASE_PATH
)
public interface DepartmentCodeClientV1 extends DepartmentCodeApiV1 {}
