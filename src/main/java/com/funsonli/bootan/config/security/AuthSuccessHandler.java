package com.funsonli.bootan.config.security;

import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.CommonUtil;
import com.funsonli.bootan.common.vo.TokenUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Component
@Slf4j
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) ((UserDetails)authentication.getPrincipal()).getAuthorities();
        List<String> permissions = new ArrayList<>();
        for(GrantedAuthority v : authorities){
            permissions.add(v.getAuthority());
        }

        TokenUser tokenUser = new TokenUser(username, permissions);

        String accessToken = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set(CommonConstant.REDIS_USER_TOKEN + username, accessToken, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(CommonConstant.REDIS_TOKEN_DETAIL + accessToken, new Gson().toJson(tokenUser), 7, TimeUnit.DAYS);

        CommonUtil.responseOut(response, BaseResult.success("ok", accessToken), 200);
    }
}
