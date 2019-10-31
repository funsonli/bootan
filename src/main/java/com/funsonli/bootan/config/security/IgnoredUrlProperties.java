package com.funsonli.bootan.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlProperties {
    private List<String> urls = new ArrayList<String>();
}
