package ru.ryatronth.sd.logging.utils;

import java.util.Map;

import org.slf4j.MDC;

public final class MdcUtils {

    private MdcUtils() {
    }

    public static void putIfNotBlank(String key, String value) {
        if (value == null) {
            return;
        }
        String v = value.trim();
        if (v.isEmpty()) {
            return;
        }
        MDC.put(key, v);
    }

    public static String get(String key) {
        return MDC.get(key);
    }

    public static Map<String, String> copy() {
        Map<String, String> ctx = MDC.getCopyOfContextMap();
        return ctx == null ? Map.of() : ctx;
    }

    public static void clear() {
        MDC.clear();
    }

}
