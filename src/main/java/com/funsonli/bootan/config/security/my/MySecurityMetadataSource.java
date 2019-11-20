package com.funsonli.bootan.config.security.my;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.Permission;
import com.funsonli.bootan.module.base.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

/**
 * @author Funson
 * @date 2019/10/9
 */
@Slf4j
@Component
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PermissionService permissionService;

    private Map<String, Collection<ConfigAttribute>> mapAttr = null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (null == mapAttr || 0 == mapAttr.size()) {
            loadAuthUrls();
        }
        log.debug("auth map:" + mapAttr.toString());

        String url = ((FilterInvocation) o).getRequestUrl();
        if (url.indexOf("\\?") > 0) {
            url = url.substring(0, url.indexOf("?") - 1);
        }
        log.debug("auth request url: " + url);
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = mapAttr.keySet().iterator();
        while (iterator.hasNext()) {
            String authUrl = iterator.next();
            log.debug("auth authUrl" + authUrl);
            if (StrUtil.isNotBlank(authUrl) && pathMatcher.match(authUrl, url)) {
                log.debug("match authUrl " + authUrl);
                return mapAttr.get(authUrl);
            }
        }

        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    private void loadAuthUrls() {
        mapAttr = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;

        List<Permission> permissions = permissionService.findByLevelAndStatusOrderBySortOrder(CommonConstant.PERMISSION_LEVEL_3, CommonConstant.STATUS_ENABLE);

        for (Permission permission : permissions) {
            if (StrUtil.isNotBlank(permission.getTitle()) && StrUtil.isNotBlank(permission.getPath())) {
                configAttributes = new ArrayList<>();
                ConfigAttribute configAttribute = new SecurityConfig(permission.getTitle());

                configAttributes.add(configAttribute);
                mapAttr.put(permission.getPath(), configAttributes);
            }
        }
        log.debug("auth permissions " + permissions.toString());
    }
}
