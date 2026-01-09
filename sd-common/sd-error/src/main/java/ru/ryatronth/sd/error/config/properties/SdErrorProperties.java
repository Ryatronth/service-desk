package ru.ryatronth.sd.error.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "sd.error")
public class SdErrorProperties {

    private boolean enabled = true;

    private boolean includeTraceId = true;

    private String traceIdMdcKey = "traceId";

}
