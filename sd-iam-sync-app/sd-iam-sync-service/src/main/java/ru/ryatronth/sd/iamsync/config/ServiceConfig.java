package ru.ryatronth.sd.iamsync.config;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.ryatronth.sd.iamsync.config.properties.IamSyncSchedulerProperties;

@Configuration
@EnableScheduling
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@EnableConfigurationProperties(IamSyncSchedulerProperties.class)
public class ServiceConfig {
}
