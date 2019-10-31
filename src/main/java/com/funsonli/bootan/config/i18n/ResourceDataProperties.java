package com.funsonli.bootan.config.i18n;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 国际化配置
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.messages")
public class ResourceDataProperties {
    private String basename;
    private Integer cacheSecond;
    private String encoding;
}

