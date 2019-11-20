package com.funsonli.bootan.config.security;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.CommonUtil;
import com.funsonli.bootan.common.vo.TokenUser;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
public class AuthFilter extends BasicAuthenticationFilter {
    private StringRedisTemplate stringRedisTemplate;

    public AuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public AuthFilter(AuthenticationManager authenticationManager, StringRedisTemplate stringRedisTemplate) {
        super(authenticationManager);
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken = request.getHeader(CommonConstant.SECURITY_ACCESS_TOKEN);
        if (StrUtil.isBlank(accessToken)) {
            accessToken = request.getParameter(CommonConstant.SECURITY_ACCESS_TOKEN);
        }

        if (StrUtil.isBlank(accessToken)) {
            CommonUtil.responseOut(response, BaseResult.error(401, "请登录"));
            return;
        }

        String str = stringRedisTemplate.opsForValue().get(CommonConstant.REDIS_TOKEN_DETAIL + accessToken);
        if (StrUtil.isBlank(str)) {
            CommonUtil.responseOut(response, BaseResult.error(401, "登录已经失效，请重新登录"));
            return;
        }

        TokenUser tokenUser = new Gson().fromJson(str, TokenUser.class);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (tokenUser.getPermissions().size() > 0) {
            for (String p : tokenUser.getPermissions()) {
                authorities.add(new SimpleGrantedAuthority(p));
            }
        }
        User user = new User(tokenUser.getUsername(), "", authorities);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}
