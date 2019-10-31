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

        String url = ((FilterInvocation) o).getRequestUrl();
        if (url.indexOf("\\?") > 0) {
            url = url.substring(0, url.indexOf("?") - 1);
        }
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = mapAttr.keySet().iterator();
        while (iterator.hasNext()) {
            String authUrl = iterator.next();
            if (StrUtil.isNotBlank(authUrl) && pathMatcher.match(authUrl, url)) {
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

    public void loadAuthUrls() {
        mapAttr = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;

        List<Permission> permissions = permissionService.findByTypeAndStatusOrderBySortOrder(CommonConstant.TYPE_DEFAULT, CommonConstant.STATUS_ENABLE);

        for (Permission permission : permissions) {
            if (StrUtil.isNotBlank(permission.getName()) && StrUtil.isNotBlank(permission.getPath())) {
                configAttributes = new ArrayList<>();
                ConfigAttribute configAttribute = new SecurityConfig(permission.getName());

                configAttributes.add(configAttribute);
                mapAttr.put(permission.getPath(), configAttributes);
            }
        }

    }
}
