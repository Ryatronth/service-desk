package ru.ryatronth.sd.department.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sd.departments-sync.scheduler")
public class DepartmentsSyncProperties {

  private long initialDelayMs = 10_000;

  private long fixedDelayMs = 300_000;

}
