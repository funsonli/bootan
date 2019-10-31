package com.funsonli.bootan.base;

import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@Component
public class BootanUser {

    @Autowired
    private UserService userService;

    public User me() {
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(user.getUsername());
    }
}
