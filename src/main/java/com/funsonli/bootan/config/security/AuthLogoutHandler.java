package com.funsonli.bootan.config.security;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.CommonUtil;
import com.funsonli.bootan.common.vo.TokenUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
public class AuthLogoutHandler implements LogoutSuccessHandler {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        String accessToken = request.getHeader(CommonConstant.SECURITY_ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) {
            accessToken = request.getParameter(CommonConstant.SECURITY_ACCESS_TOKEN);
        }

        if (null != accessToken) {
            String tokenDetail = redisTemplate.opsForValue().get(CommonConstant.REDIS_TOKEN_DETAIL + accessToken);

            if (null != tokenDetail) {
                TokenUser tokenUser = new Gson().fromJson(tokenDetail, TokenUser.class);
                String username = tokenUser.getUsername();
                System.out.println(redisTemplate.delete(CommonConstant.REDIS_USER_TOKEN + username));
            }

            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(200, "注销成功", null), 200);
        } else {
            CommonUtil.responseOut(httpServletResponse, BaseResult.ret(401, "请先登录", null), 401);
        }
    }
}
