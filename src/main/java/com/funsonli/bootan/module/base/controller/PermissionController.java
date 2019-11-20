package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.CommonUtil;
import com.funsonli.bootan.module.base.entity.Permission;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.PermissionService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("权限接口")
@RequestMapping("/bootan/permission")
public class PermissionController extends BaseController<Permission, String> {

    @Autowired
    private PermissionService modelService;

    @Override
    public BaseService<Permission, String> getService() {
        return modelService;
    }

    @Override
    @PostMapping("/save")
    @ApiOperation("保存")
    public BaseResult save(@ModelAttribute Permission modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (modelAttribute.getParentId() == null) {
            modelAttribute.setParentId(CommonConstant.DEFAULT_PARENT_ID);
        }

        // level 需要计算
        if (CommonConstant.DEFAULT_PARENT_ID.equals(modelAttribute.getParentId())) {
            modelAttribute.setLevel(CommonConstant.PERMISSION_LEVEL_0);
        } else {
            Permission parent = modelService.findById(modelAttribute.getParentId());
            if (null != parent && (0 <= parent.getLevel() && parent.getLevel() <= 2)) {
                modelAttribute.setLevel(parent.getLevel() + 1);
            } else {
                return BaseResult.error();
            }
        }

        Permission model = modelService.save(modelAttribute);

        return BaseResult.success(model);
    }

    @Override
    @PostMapping("/create")
    @ApiOperation("创建")
    public BaseResult create(@ModelAttribute Permission modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (!StringUtils.isEmpty(modelAttribute.getId())) {
            return error();
        }

        return update(modelAttribute, result, request, response);
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("更新")
    public BaseResult update(@ModelAttribute Permission modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (modelAttribute.getParentId() == null) {
            modelAttribute.setParentId(CommonConstant.DEFAULT_PARENT_ID);
        }

        // level 需要计算
        if (CommonConstant.DEFAULT_PARENT_ID.equals(modelAttribute.getParentId())) {
            modelAttribute.setLevel(CommonConstant.PERMISSION_LEVEL_0);
        } else {
            Permission parent = modelService.findById(modelAttribute.getParentId());
            if (null != parent && (0 <= parent.getLevel() && parent.getLevel() <= 2)) {
                modelAttribute.setLevel(parent.getLevel() + 1);
            } else {
                return BaseResult.error();
            }
        }

        Permission model = modelService.save(modelAttribute);

        return BaseResult.success(model);
    }

    @Override
    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    public BaseResult delete(@PathVariable String[] ids) {
        for (String id : ids) {
            modelService.delete(id);
        }

        return BaseResult.success("批量删除数据成功");
    }

    @Override
    @GetMapping("/all")
    @ApiOperation("返回所有数据，需谨慎")
    public BaseResult all() {
        List<Permission> models = modelService.findAllBySortOrder();

        return BaseResult.success(convert(models, 1));
    }

    @GetMapping("/menu-list")
    @ApiOperation("前端用户菜单")
    public BaseResult menuList(HttpServletResponse response) {
        User user = bootanUser.me();

        if (user != null) {
            List<Permission> models = modelService.findByUserId(bootanUser.me().getId());
            log.info(models.toString());

            return BaseResult.success(convert(models, 2));
        } else {
            CommonUtil.responseOut(response, BaseResult.error(401, "请登录"));
            return BaseResult.error();
        }
    }

    private List<Permission> convert(List<Permission> models, int type) {

        List<Permission> list0 = new ArrayList<>();
        for (Permission model : models) {
            if (CommonConstant.PERMISSION_LEVEL_0.equals(model.getLevel())) {
                list0.add(model);
            }
        }

        List<Permission> list1 = new ArrayList<>();
        for (Permission model : models) {
            if (CommonConstant.PERMISSION_LEVEL_1.equals(model.getLevel())) {
                list1.add(model);
            }
        }

        List<Permission> list2 = new ArrayList<>();
        for (Permission model : models) {
            if (CommonConstant.PERMISSION_LEVEL_2.equals(model.getLevel())) {
                list2.add(model);
            }
        }

        List<Permission> list3 = new ArrayList<>();
        for (Permission model : models) {
            if (CommonConstant.PERMISSION_LEVEL_3.equals(model.getLevel())) {
                list3.add(model);
            }
        }

        // 2级
        for (Permission m2 : list2) {
            List<String> listPerm = new ArrayList<>();
            List<Permission> listTemp = new ArrayList<>();
            for (Permission m3 : list3) {
                if (m2.getId().equals(m3.getParentId())) {
                    listPerm.add(m3.getButtonType());
                    listTemp.add(m3);
                }
            }
            m2.setPermTypes(listPerm);
            if (1 == type) {
                m2.setChildren(listTemp);
            }
        }

        for (Permission m1 : list1) {
            List<Permission> listTemp = new ArrayList<>();
            for (Permission m2 : list2) {
                if (m1.getId().equals(m2.getParentId())) {
                    listTemp.add(m2);
                }
            }
            m1.setChildren(listTemp);
        }

        for (Permission m0 : list0) {
            List<Permission> listTemp = new ArrayList<>();
            for (Permission m1 : list1) {
                if (m0.getId().equals(m1.getParentId())) {
                    listTemp.add(m1);
                }
            }
            m0.setChildren(listTemp);
        }

        return list0;
    }
}
