package ru.ryatronth.sd.file.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "sd.s3")
public class S3Properties {

  private String accessKey;

  private String secretKey;

  private String endpoint;

  private String bucket;

  private String keyPrefix;

  private Long urlExpirationMinutes;

}
