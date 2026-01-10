package ru.ryatronth.sd.logging.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ryatronth.sd.logging.config.properties.MdcKeys;
import ru.ryatronth.sd.logging.config.properties.SdLoggingProperties;
import ru.ryatronth.sd.logging.utils.TraceIdProvider;
import ru.ryatronth.sd.security.utils.SecurityUtils;

@RequiredArgsConstructor
public class MdcLoggingFilter extends OncePerRequestFilter {

  private final SdLoggingProperties properties;

  private final SecurityUtils securityUtils;

  private final TraceIdProvider traceIdProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {

    String headerName = properties.getHttp().getTraceIdHeader();
    String traceId =
        Optional.ofNullable(request.getHeader(headerName)).filter(v -> !v.isBlank())
            .orElseGet(traceIdProvider::generate);

    try {
      MDC.put(MdcKeys.TRACE_ID, traceId);

      String userId = resolveUserId();
      if (userId != null && !userId.isBlank()) {
        MDC.put(MdcKeys.USER_ID, userId);
      }

      if (properties.getHttp().isWriteTraceIdToResponse()) {
        response.setHeader(headerName, traceId);
      }

      filterChain.doFilter(request, response);
    } finally {
      MDC.remove(MdcKeys.TRACE_ID);
      MDC.remove(MdcKeys.USER_ID);
    }
  }

  private String resolveUserId() {
    return securityUtils.currentUserId().orElse(null);
  }

}
