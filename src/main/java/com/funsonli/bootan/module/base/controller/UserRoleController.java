package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.UserRole;
import com.funsonli.bootan.module.base.service.UserRoleService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 用户角色接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("用户角色接口")
@RequestMapping("/bootan/user-role")
public class UserRoleController extends BaseController<UserRole, String> {

    @Autowired
    private UserRoleService modelService;

    @Override
    public BaseService getService() {
        return modelService;
    }

}
