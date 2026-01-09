package ru.ryatronth.sd.logging.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import ru.ryatronth.sd.logging.config.properties.MdcKeys;
import ru.ryatronth.sd.logging.config.properties.SdLoggingProperties;

@RequiredArgsConstructor
public class TraceIdFeignRequestInterceptor implements RequestInterceptor {

    private final SdLoggingProperties properties;

    @Override
    public void apply(RequestTemplate template) {
        String traceId = MDC.get(MdcKeys.TRACE_ID);
        if (traceId == null || traceId.isBlank()) {
            return;
        }
        template.header(properties.getHttp().getTraceIdHeader(), traceId);
    }

}
