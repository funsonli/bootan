package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.RoleDepartment;
import com.funsonli.bootan.module.base.service.RoleDepartmentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 角色部门数据范围接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("角色部门数据范围接口")
@RequestMapping("/bootan/role-department")
public class RoleDepartmentController extends BaseController<RoleDepartment, String> {

    @Autowired
    private RoleDepartmentService modelService;

    @Override
    public RoleDepartmentService getService() {
        return modelService;
    }

}
