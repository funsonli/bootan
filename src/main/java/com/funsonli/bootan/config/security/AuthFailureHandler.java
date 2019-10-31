package com.funsonli.bootan.config.security;

import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Component
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        if (e instanceof BadCredentialsException || e instanceof UsernameNotFoundException) {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "账户名或者密码输入错误", null));
        } else if (e instanceof LockedException) {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "账户被锁定，请联系管理员!", null));
        } else if (e instanceof CredentialsExpiredException) {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "密码过期，请联系管理员!", null));
        } else if (e instanceof AccountExpiredException) {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "账户过期，请联系管理员!", null));
        } else if (e instanceof DisabledException) {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "账户被禁用，请联系管理员!", null));
        } else {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(500, "登录失败", null));
        }
    }
}
