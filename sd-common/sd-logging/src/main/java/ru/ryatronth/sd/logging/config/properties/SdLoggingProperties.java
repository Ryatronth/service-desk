package ru.ryatronth.sd.logging.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "sd.logging")
public class SdLoggingProperties {

  private boolean enabled = true;

  private Http http = new Http();

  @Setter
  @Getter
  public static class Http {

    private String traceIdHeader = "X-Trace-Id";

    private boolean writeTraceIdToResponse = true;

  }

}
