package com.funsonli.bootan.module.base.controller;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public BaseService getService() {
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

    @RequestMapping("/bootan/me")
    public BaseResult me() {
        Map<String, String> result = new HashMap<>(16);
        result.put("username", "funson");
        result.put("age", "30");
        return this.success(result);
    }

    @RequestMapping("/bootan/message-count")
    public BaseResult messageCount() {
        return this.success((Integer)9);
    }
}

