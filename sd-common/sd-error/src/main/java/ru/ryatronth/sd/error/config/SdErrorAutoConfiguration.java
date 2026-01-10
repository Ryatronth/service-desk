package ru.ryatronth.sd.error.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.ryatronth.sd.error.config.properties.SdErrorProperties;
import ru.ryatronth.sd.error.web.ErrorResponseFactory;
import ru.ryatronth.sd.error.web.GlobalExceptionHandler;

@AutoConfiguration
@EnableConfigurationProperties(SdErrorProperties.class)
@ConditionalOnProperty(prefix = "sd.error", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(name = "org.springframework.web.bind.annotation.RestControllerAdvice")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SdErrorAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public ErrorResponseFactory errorResponseFactory(SdErrorProperties properties) {
    return new ErrorResponseFactory(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public GlobalExceptionHandler globalExceptionHandler(ErrorResponseFactory factory) {
    return new GlobalExceptionHandler(factory);
  }

}
