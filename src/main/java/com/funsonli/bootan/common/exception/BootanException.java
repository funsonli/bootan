package com.funsonli.bootan.common.exception;

import lombok.Data;

/**
 * 自定义异常
 * https://github.com/funsonli/spring-boot-demo/tree/master/spring-boot-demo-130-exception
 * @author Funsonli
 * @date 2019/11/19
 */
@Data
public class BootanException extends RuntimeException {

    private String message;

    public BootanException(String message) {
        super(message);
        this.message = message;
    }

}
