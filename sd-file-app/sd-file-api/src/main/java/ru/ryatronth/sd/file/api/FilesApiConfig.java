package ru.ryatronth.sd.file.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;

@Configuration
@EnableFeignClients
@EnableConfigFiles("file-client.yml")
public class FilesApiConfig {
}
