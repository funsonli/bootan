package com.funsonli.bootan.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class for
 *
 * @author Funson
 * @date 2019/10/12
 */
@Data
@Component
@ConfigurationProperties(prefix = "demo.book")
public class JedisProperties {
    private String name;
    private Integer price;
    private List<String> authors;
}
