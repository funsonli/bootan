package com.funsonli.bootan.module.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/11/5
 */
@Data
@AllArgsConstructor
public class RedisVO {
    private String key;
    private String value;
}
