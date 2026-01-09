package ru.ryatronth.sd.logging.utils;

import org.slf4j.MDC;
import ru.ryatronth.sd.logging.config.properties.MdcKeys;

import java.util.Optional;
import java.util.UUID;

public class TraceIdProvider {

    public String generate() {
        return UUID.randomUUID().toString();
    }

    public Optional<String> currentTraceId() {
        return Optional.ofNullable(MDC.get(MdcKeys.TRACE_ID));
    }

}
