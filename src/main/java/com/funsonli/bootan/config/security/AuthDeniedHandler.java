package com.funsonli.bootan.config.security;

import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class AuthDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        CommonUtil.responseOut(httpServletResponse, BaseResult.ret(403, "没有权限，请联系管理员", null));
    }
}
