package com.funsonli.bootan.module.base.controller;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Department;
import com.funsonli.bootan.module.base.entity.Role;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.entity.UserRole;
import com.funsonli.bootan.module.base.service.DepartmentService;
import com.funsonli.bootan.module.base.service.RoleService;
import com.funsonli.bootan.module.base.service.UserService;
import com.funsonli.bootan.module.base.service.UserRoleService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@RequestMapping("/bootan/user")
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("用户接口")
public class UserController extends BaseController<User, String> {
    @Autowired
    private UserService modelService;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public BaseService getService() {
        return modelService;
    }

    @Override
    @GetMapping({"/", "index"})
    public BaseResult index(@ModelAttribute User user,
                            @ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (result.hasErrors()) {
            return BaseResult.error();
        }

        Page<User> page = modelService.findByCondition(user, searchVO, PageUtil.initPage(pageVO));

        for (User model : page.getContent()) {
            if(StrUtil.isNotBlank(model.getDepartmentId())){
                Department department = departmentService.findById(model.getDepartmentId());
                if (null != department) {
                    model.setDepartmentName(department.getName());
                }
            }
            List<Role> roles = roleService.findByUserId(model.getId());
            model.setRoles(roles);

            entityManager.clear();
            model.setPassword(null);

        }

        return BaseResult.success(page);

    }

    @Override
    @PostMapping("/save")
    @ApiOperation("保存")
    public BaseResult save(@ModelAttribute User modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        User model;
        if (StrUtil.isNotEmpty(id)) {
            model = modelService.findById(id);
            if (null == model) {
                return BaseResult.error("输入参数不正确");
            }

            if (!model.getUsername().equals(modelAttribute.getUsername()) && null != modelService.findByUsername(modelAttribute.getUsername())) {
                return BaseResult.error("用户名已存在");
            }
            if (!model.getEmail().equals(modelAttribute.getEmail()) && null != modelService.findByEmail(modelAttribute.getEmail())) {
                return BaseResult.error("邮箱已存在");
            }
            if (!model.getMobile().equals(modelAttribute.getMobile()) && null != modelService.findByMobile(modelAttribute.getMobile())) {
                return BaseResult.error("手机号已存在");
            }
            modelAttribute.setPassword(model.getPassword());
        } else {
            if (null != modelService.findByUsername(modelAttribute.getUsername())) {
                return BaseResult.error("用户名已存在");
            }
            if (null != modelService.findByEmail(modelAttribute.getEmail())) {
                return BaseResult.error("邮箱已存在");
            }
            if (null != modelService.findByMobile(modelAttribute.getMobile())) {
                return BaseResult.error("手机号已存在");
            }
            modelAttribute.setPassword(new BCryptPasswordEncoder().encode(modelAttribute.getPassword()));
        }

        model = modelService.save(modelAttribute);
        if (null == model) {
            return BaseResult.error();
        }

        String[] roles = request.getParameter("roles").split(",");
        userRoleService.deleteByUserId(modelAttribute.getId());
        if (0 < roles.length) {
            for (String roleId : roles) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(modelAttribute.getId());
                userRoleService.save(userRole);
            }
        }

        return BaseResult.success(model);
    }

    @Override
    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    public BaseResult delete(@PathVariable String[] ids) {
        for (String id : ids) {
            modelService.delete(id);
            userRoleService.deleteByUserId(id);
        }

        if (1 < ids.length) {
            return BaseResult.success("批量删除数据成功");
        }

        return BaseResult.success();
    }

    @Override
    @GetMapping("/view/{id}")
    @ApiOperation("查看单个数据详情")
    public BaseResult view(@PathVariable String id) {
        User model = modelService.findById(id);
        if(StrUtil.isNotBlank(model.getDepartmentId())){
            Department department = departmentService.findById(model.getDepartmentId());
            if (null != department) {
                model.setDepartmentName(department.getName());
            }
        }
        List<Role> roles = roleService.findByUserId(model.getId());
        model.setRoles(roles);

        return BaseResult.success(model);
    }

    @Override
    @PostMapping("/import-data")
    @ApiOperation("导入数据")
    public BaseResult importData(@RequestBody List<User> models) {

        List<String> reasons = new ArrayList<>();
        int i = 0;
        for (User model : models) {
            i++;

            if (StrUtil.isBlank(model.getUsername()) || StrUtil.isBlank(model.getPassword())) {
                reasons.add("第" + i + "行：用户名或密码为空<br>");
                continue;
            }

            if (null != modelService.findByUsername(model.getUsername())) {
                reasons.add("第" + i + "行：用户名已存在<br>");
                continue;
            }

            if (StrUtil.isNotBlank(model.getDepartmentId())) {
                Department department = departmentService.findById(model.getDepartmentId());
                if (null == department) {
                    reasons.add("第" + i + "行：部门不存在<br>");
                    continue;
                }
            }

            Role role = roleService.findByIsDefault(CommonConstant.ROLE_DEFAULT_YES);
            if (null != role) {
                UserRole userRole = new UserRole();
                userRole.setUserId(model.getId());
                userRole.setRoleId(role.getId());
                userRoleService.save(userRole);
            }

            if (null == model.getStatus()) {
                model.setStatus(CommonConstant.STATUS_ENABLE);
            }

            model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
            modelService.save(model);
        }

        StringBuilder message = new StringBuilder();
        if (0 < reasons.size()) {
            message.append("部分导入成功，成功").append(models.size() - reasons.size()).append("条，失败").append(reasons.size()).append("条");
            message.append(reasons.toString());
        } else {
            message.append("全部导入成功，共").append(models.size() - reasons.size()).append("条");
        }

        return BaseResult.success(message);
    }

    @GetMapping("/department-user/{departmentId}")
    @ApiOperation("返回所有数据，需谨慎")
    public BaseResult departmentUser(@PathVariable String departmentId) {
        List<User> models = modelService.findByDepartmentId(departmentId);

        return BaseResult.success(models);
    }

    @PostMapping("/unlock")
    @ApiOperation("验证当前用户密码解锁后续操作")
    public BaseResult unlock(@RequestParam String password) {

        if (!new BCryptPasswordEncoder().matches(password, bootanUser.me().getPassword())) {
            return BaseResult.error("密码错误");
        }

        return BaseResult.success();
    }

    @PostMapping("/reset-password")
    @ApiOperation("重置密码")
    public BaseResult resetPassword(@RequestParam String[] ids) {

        for (String id : ids) {
            User model = modelService.findById(id);
            if (null != model) {
                model.setPassword(new BCryptPasswordEncoder().encode("123456"));
                modelService.save(model);
            }
        }

        return BaseResult.success();
    }
}
