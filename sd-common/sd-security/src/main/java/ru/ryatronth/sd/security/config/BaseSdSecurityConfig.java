package ru.ryatronth.sd.security.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.ryatronth.sd.property.reader.EnableConfigFiles;
import ru.ryatronth.sd.security.config.properties.SdKeycloakProperties;
import ru.ryatronth.sd.security.config.properties.SdSecurityProperties;

@Configuration
@EnableConfigFiles("classpath:sd-security.yml")
@EnableConfigurationProperties({SdSecurityProperties.class, SdKeycloakProperties.class})
public class BaseSdSecurityConfig {
}
