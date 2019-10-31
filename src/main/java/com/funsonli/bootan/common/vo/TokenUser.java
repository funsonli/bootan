package com.funsonli.bootan.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 登录用户
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@AllArgsConstructor
public class TokenUser {

    private String username;

    private List<String> permissions;
}
