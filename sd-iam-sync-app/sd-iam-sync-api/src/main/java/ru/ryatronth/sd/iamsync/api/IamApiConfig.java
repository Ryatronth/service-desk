package ru.ryatronth.sd.iamsync.api;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;

@Configuration
@EnableFeignClients
@EnableConfigFiles("sync-users-client.yml")
public class IamApiConfig {}
