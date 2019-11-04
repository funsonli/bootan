package com.funsonli.bootan.config.security;

import com.funsonli.bootan.config.security.my.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IgnoredUrlProperties ignoredUrlProperties;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private AuthSuccessHandler authSuccessHandler;

    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private AuthLogoutHandler authLogoutHandler ;

    @Autowired
    private AuthDeniedHandler authDeniedHandler;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // 所有请求都要认证
                .anyRequest().authenticated()
                .and()

                // 设置登陆页
                .formLogin()
                .loginPage("/bootan/no-auth")
                .loginProcessingUrl("/bootan/login")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)

                .and()
                .logout().logoutUrl("/bootan/logout").permitAll().logoutSuccessHandler(authLogoutHandler)

                .and()
                .csrf().disable().exceptionHandling().accessDeniedHandler(authDeniedHandler)

                .and()
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                .addFilter(new AuthFilter(authenticationManager(), stringRedisTemplate));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //放行配置文件中的路径
        for (String url : ignoredUrlProperties.getUrls()) {
            web.ignoring().antMatchers(url);
        }
    }
}