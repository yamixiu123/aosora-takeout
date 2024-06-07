package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.aws")
@Data
public class AwsProperties {

    private String region;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}
