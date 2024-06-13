package com.sky.config;


import com.sky.properties.AwsProperties;
import com.sky.utils.AwsBucketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public AwsBucketUtil awsBucketUtil(AwsProperties awsProperties) {
        log.info("开始创建aws云文件{}", awsProperties);
        return new AwsBucketUtil(
                awsProperties.getRegion(),
                awsProperties.getAccessKeyId(),
                awsProperties.getAccessKeySecret(),
                awsProperties.getBucketName()
        );
    }
}
