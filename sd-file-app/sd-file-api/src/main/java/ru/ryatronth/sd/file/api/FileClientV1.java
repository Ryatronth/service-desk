package ru.ryatronth.sd.file.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
    name = "sd-file",
    url = "${sd.clients.files.url}",
    path = FileApiV1.BASE_PATH
)
public interface FileClientV1 extends FileApiV1 {
}
