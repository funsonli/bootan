package com.funsonli.bootan.config.security;

import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.Permission;
import com.funsonli.bootan.module.base.entity.Role;
import com.funsonli.bootan.module.base.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
public class SecurityUserDetail extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    public SecurityUserDetail(User user) {
        if (null != user) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setStatus(user.getStatus());
            this.setRoles(user.getRoles());
            this.setPermissions(user.getPermissions());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Permission> permissions = this.getPermissions();

        if (null != permissions && 0 < permissions.size()) {
            for (Permission v : permissions) {
                if (v.getTitle().length() > 0) {
                    authorities.add(new SimpleGrantedAuthority(v.getTitle()));
                }
            }
        }

        List<Role> roles = this.getRoles();
        if (null != roles && 0 < roles.size()) {
            for (Role v : roles) {
                authorities.add(new SimpleGrantedAuthority(v.getName()));
            }
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return CommonConstant.STATUS_ENABLE.equals(this.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return CommonConstant.STATUS_ENABLE.equals(this.getStatus());
    }
}
