package ru.ryatronth.sd.iamsync.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sd.iam-sync.scheduler")
public class IamSyncSchedulerProperties {

    private long initialDelayMs = 10_000;

    private long fixedDelayMs = 300_000;

}
