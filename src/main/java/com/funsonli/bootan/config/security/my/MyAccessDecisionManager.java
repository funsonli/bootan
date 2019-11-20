package com.funsonli.bootan.config.security.my;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * Class for
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        log.debug("auth configAttributes " + configAttributes.toString());
        if (null == configAttributes) {
            return;
        }

        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()){
            ConfigAttribute c = iterator.next();
            String need = c.getAttribute();
            log.debug("auth need: " + need + " grant auths: " + authentication.getAuthorities().toString());
            for (GrantedAuthority v : authentication.getAuthorities()) {
                if (need.trim().equals(v.getAuthority())) {
                    return;
                }
            }
        } throw new AccessDeniedException("没有访问权限，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
