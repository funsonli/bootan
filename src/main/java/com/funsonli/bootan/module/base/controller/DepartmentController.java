package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.base.PageContent;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @PostMapping("/save")
    @ApiOperation("保存")
    public BaseResult save(@ModelAttribute Department modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        Department model = modelService.save(modelAttribute);

        // 更新父节点is_parent
        if (!CommonConstant.DEFAULT_PARENT_ID.equals(model.getParentId())) {
            Department parent = modelService.findById(model.getParentId());
            if (null != parent && (null == parent.getIsParent() || !parent.getIsParent())) {
                parent.setIsParent(true);
                modelService.save(parent);
            }
        }

        // 删除子部门需要判断父部门还有没有子节点 is_parent
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
