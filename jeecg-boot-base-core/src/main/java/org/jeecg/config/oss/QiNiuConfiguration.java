package org.jeecg.config.oss;

import lombok.Data;
import org.jeecg.common.util.qiniu.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 云存储 配置
 *
 * @author: jeecg-boot
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jeecg.qiniu")
public class QiNiuConfiguration {

    @Value("${jeecg.qiniu.accessKey}")
    private String accessKeyId;
    @Value("${jeecg.qiniu.secretKey}")
    private String accessKeySecret;
    @Value("${jeecg.qiniu.bucketName}")
    private String bucketName;
    @Value("${jeecg.qiniu.staticDomain:}")
    private String staticDomain;

}