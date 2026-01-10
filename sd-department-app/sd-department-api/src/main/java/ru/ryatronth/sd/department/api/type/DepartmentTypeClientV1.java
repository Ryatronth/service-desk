package ru.ryatronth.sd.department.api.type;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-department-type",
    url = "${sd.clients.department.url}",
    path = DepartmentTypeApiV1.BASE_PATH
)
public interface DepartmentTypeClientV1 extends DepartmentTypeApiV1 {
}
