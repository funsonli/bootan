package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.base.PageContent;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.Department;
import com.funsonli.bootan.module.base.service.DepartmentService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("部门接口")
@RequestMapping("/bootan/department")
public class DepartmentController extends BaseController<Department, String> {

    @Autowired
    private DepartmentService modelService;

    @Override
    public BaseService<Department, String> getService() {
        return modelService;
    }

    @Override
    @GetMapping("/all")
    @ApiOperation("返回所有数据，需谨慎")
    @BootanLog(value = "返回所有数据", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult all() {

        List<Department> models = getService().findAll();

        Map<String, Department> mapAll = new HashMap<>(16);
        models.forEach(model -> mapAll.put(model.getId(), model));

        List<Department> list = new ArrayList<>();
        for (Department model : models) {
            model.setTitle(model.getName());
            if (CommonConstant.DEFAULT_PARENT_ID.equals(model.getParentId())) {
                model.setParentName("一级部门");
            } else {
                Department parent = mapAll.get(model.getParentId());
                if (null != parent) {
                    model.setParentName(parent.getName());
                } else {
                    model.setParentName("");
                }
            }

            if (CommonConstant.DEFAULT_PARENT_ID.equals(model.getParentId())) {
                list.add(model);
            }
        }

        for (Department item : list) {
            item.setChildren(getChildren(item.getId(), models));
        }
        return this.success(list);
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    private List<Department> getChildren(String id, List<Department> models) {
        // 子菜单
        List<Department> childList = new ArrayList<>();
        for (Department menu : models) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (!CommonConstant.DEFAULT_PARENT_ID.equals(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Department menu : childList) {
            menu.setChildren(getChildren(menu.getId(), models));
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

    @Override
    @PostMapping("/save")
    @ApiOperation("保存")
    public BaseResult save(@ModelAttribute Department modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Department model = modelService.save(modelAttribute);

        // 更新父节点is_parent
        if (!CommonConstant.DEFAULT_PARENT_ID.equals(model.getParentId())) {
            Department parent = modelService.findById(model.getParentId());
            if (null != parent) {
                model.setLevel(parent.getLevel() + 1);
                modelService.save(model);
            }
            if (null != parent && (null == parent.getIsParent() || !parent.getIsParent())) {
                parent.setIsParent(true);
                modelService.save(parent);
            }
        }

        // 移动子部门需要判断父部门还有没有子节点 is_parent
        List<Department> all = modelService.findAll();
        Map<String, String> mapAll = new HashMap<>(16);
        all.forEach(m -> mapAll.put(m.getParentId(), m.getId()));
        for (Department m : all) {
            if (!mapAll.containsKey(m.getId())) {
                m.setIsParent(false);
                modelService.saveAndFlush(m);
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
        }

        // 删除子部门需要判断父部门还有没有子节点 is_parent
        List<Department> all = modelService.findAll();
        Map<String, String> mapAll = new HashMap<>(16);
        all.forEach(model -> mapAll.put(model.getParentId(), model.getId()));
        for (Department model : all) {
            if (!mapAll.containsKey(model.getId())) {
                model.setIsParent(false);
                modelService.saveAndFlush(model);
            }
        }

        return BaseResult.success("批量删除数据成功");
    }

    @GetMapping("/list/{parentId}")
    @ApiOperation("根据父ID获取子数据")
    public BaseResult list(@PathVariable String parentId) {
        List<Department> models = modelService.findByParentId(parentId);

        List<Department> all = modelService.findAllBySortOrder();
        Map<String, Department> mapAll = new HashMap<>(16);
        all.forEach(model -> mapAll.put(model.getId(), model));

        models.forEach(model -> {
            if (CommonConstant.DEFAULT_PARENT_ID.equals(model.getParentId())) {
                model.setParentName("一级部门");
            } else {
                Department parent = mapAll.get(model.getParentId());
                if (null != parent) {
                    model.setParentName(parent.getName());
                } else {
                    model.setParentName("");
                }
            }
        });

        return BaseResult.success(new PageContent(models));
    }
}
