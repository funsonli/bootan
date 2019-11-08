package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站基本功能控制器
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@RestController
public class SiteController extends BaseController<User, String> {

    @Autowired
    private UserService userService;

    @Override
    public UserService getService() {
        return userService;
    }

    @RequestMapping("/")
    public BaseResult index() {
        return this.success("welcome to bootan");
    }

    @RequestMapping("/bootan/no-auth")
    public BaseResult noAuth() {
        return this.error("no auth");
    }

    @RequestMapping("/csrf")
    public BaseResult csrf() {
        return this.success();
    }

    @RequestMapping("/bootan/me")
    public BaseResult me() {
        return this.success(bootanUser.me());
    }

    @RequestMapping("/bootan/message-count")
    public BaseResult messageCount() {
        return this.success((Integer)9);
    }
}

