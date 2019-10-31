package com.funsonli.bootan.config.i18n;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * 国际化
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@Configuration
public class ResourceConfig {

    @Autowired
    private ResourceDataProperties resourceDataProperties;

    @Bean
    @Primary
    ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource bundleMessageSource = new ResourceBundleMessageSource();
        bundleMessageSource.setDefaultEncoding(resourceDataProperties.getEncoding());
        bundleMessageSource.setBasenames(resourceDataProperties.getBasename());
        bundleMessageSource.setCacheSeconds(resourceDataProperties.getCacheSecond());
        return bundleMessageSource;
    }
}