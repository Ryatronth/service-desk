package ru.ryatronth.sd.logging.config;

import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import ru.ryatronth.sd.logging.config.properties.SdLoggingProperties;
import ru.ryatronth.sd.logging.feign.TraceIdFeignRequestInterceptor;
import ru.ryatronth.sd.logging.utils.TraceIdProvider;
import ru.ryatronth.sd.logging.web.MdcLoggingFilter;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;
import ru.ryatronth.sd.security.utils.SecurityUtils;

@AutoConfiguration
@EnableConfigurationProperties(SdLoggingProperties.class)
@EnableConfigFiles("sd-logging.yml")
@ConditionalOnProperty(prefix = "sd.logging", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SdLoggingAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public TraceIdProvider traceIdProvider() {
    return new TraceIdProvider();
  }

  @Bean
  @ConditionalOnMissingBean
  public FilterRegistrationBean<MdcLoggingFilter> mdcLoggingFilter(SdLoggingProperties properties,
                                                                   TraceIdProvider traceIdProvider,
                                                                   SecurityUtils securityUtils) {
    var bean = new FilterRegistrationBean<>(
        new MdcLoggingFilter(properties, securityUtils, traceIdProvider));
    bean.setOrder(Integer.MIN_VALUE);
    return bean;
  }

  @Bean
  @ConditionalOnClass(name = "feign.RequestInterceptor")
  @ConditionalOnMissingBean(RequestInterceptor.class)
  public RequestInterceptor traceIdFeignRequestInterceptor(SdLoggingProperties properties) {
    return new TraceIdFeignRequestInterceptor(properties);
  }

}
