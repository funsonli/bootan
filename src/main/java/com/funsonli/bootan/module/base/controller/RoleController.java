package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Role;
import com.funsonli.bootan.module.base.entity.RoleDepartment;
import com.funsonli.bootan.module.base.entity.RolePermission;
import com.funsonli.bootan.module.base.service.RoleDepartmentService;
import com.funsonli.bootan.module.base.service.RolePermissionService;
import com.funsonli.bootan.module.base.service.RoleService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("角色接口")
@RequestMapping("/bootan/role")
public class RoleController extends BaseController<Role, String> {

    @Autowired
    private RoleService modelService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private RoleDepartmentService roleDepartmentService;

    @Override
    public BaseService getService() {
        return modelService;
    }

    @Override
    @GetMapping({"/", "index"})
    @ApiOperation("角色列表搜索分页")
    public BaseResult index(@ModelAttribute Role modelAttribute,
                            @ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (result.hasErrors()) {
            return BaseResult.error();
        }

        Page<Role> page = modelService.findByCondition(modelAttribute, searchVO, PageUtil.initPage(pageVO));

        for (Role model : page.getContent()) {
            List<RolePermission> rolePermissions = rolePermissionService.findByRoleId(model.getId());
            model.setPermissions(rolePermissions);

            List<RoleDepartment> roleDepartments = roleDepartmentService.findByRoleId(model.getId());
            model.setDepartments(roleDepartments);
        }

        return BaseResult.success(page);
    }

    @RequestMapping(value = "/save-permission",method = RequestMethod.POST)
    @ApiOperation(value = "保存角色分配菜单权限")
    public BaseResult savePermission(@RequestParam String roleId,
                                     @RequestParam(required = false) String[] permissionIds) {

        rolePermissionService.deleteByRoleId(roleId);

        for (String id : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(id);
            rolePermissionService.save(rolePermission);
        }

        return BaseResult.success();
    }

    @PostMapping(value = "/save-department")
    @ApiOperation(value = "保存角色分配菜单权限")
    public BaseResult saveDepartment(@RequestParam String roleId,
                                     @RequestParam Integer departmentType,
                                     @RequestParam(required = false) String[] departmentIds) {
        Role model = modelService.findById(roleId);
        if (null == model) {
            return BaseResult.error();
        }

        model.setDepartmentType(departmentType);
        modelService.save(model);

        roleDepartmentService.deleteByRoleId(roleId);

        if (!CommonConstant.ROLE_DEPARTMENT_TYPE.equals(departmentType)) {
            for (String id : departmentIds) {
                RoleDepartment roleDepartment = new RoleDepartment();
                roleDepartment.setRoleId(roleId);
                roleDepartment.setDepartmentId(id);
                roleDepartmentService.save(roleDepartment);
            }
        }

        return BaseResult.success();
    }

    @PostMapping(value = "/save-default")
    @ApiOperation(value = "保存角色分配菜单权限")
    public BaseResult saveDefault(@RequestParam String roleId,
                                     @RequestParam Integer isDefault) {

        Role model = modelService.findById(roleId);
        if (null == model) {
            return BaseResult.error();
        }

        if (CommonConstant.ROLE_DEFAULT_NO.equals(isDefault)) {
            model.setIsDefault(CommonConstant.ROLE_DEFAULT_NO);
        } else {
            modelService.updateIsDefaultNo();
            model.setIsDefault(CommonConstant.ROLE_DEFAULT_YES);
        }

        modelService.save(model);

        return BaseResult.success();
    }
}
