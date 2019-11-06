package com.funsonli.bootan.base;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.LocaleMessage;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 基础接口，每个继承该类的控制器都有的接口
 * 
 * @author Funsonli
 * Date 2019-08-16
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("基础接口，每个继承该类的控制器都有的接口")
public abstract class BaseController<E extends BaseEntity, ID extends Serializable> {

    @Autowired
    public BootanUser bootanUser;

    @Resource
    public LocaleMessage localeMessage;

    /**
     * 获取对应的service
     * @author Funsonli
     * @date 2019/10/31
     * @return BaseService<E, ID>
     */
    @Autowired
    public abstract BaseService<E, ID> getService();

    protected BaseResult success(Object data) {

        return BaseResult.success(localeMessage.getMessage("Operation_Success"), data);
    }

    protected BaseResult success(String message) {

        return BaseResult.success(message);
    }

    protected BaseResult success(String message, Object data) {

        return BaseResult.success(message, data);
    }

    protected BaseResult success() {
        return BaseResult.success(localeMessage.getMessage("Operation_Success"));
    }

    protected BaseResult error() {

        return BaseResult.error(localeMessage.getMessage("Operation_Failed"));
    }

    protected BaseResult error(int status) {

        return BaseResult.error(status, localeMessage.getMessage("Operation_Failed"));
    }

    protected BaseResult error(String message) {

        return BaseResult.error(message);
    }

    protected BaseResult error(int status, String message) {

        return BaseResult.error(status, message);
    }

    @GetMapping({"/", "index"})
    @ApiOperation("角色部门数据范围列表搜索分页")
    @BootanLog(value = "角色部门数据范围列表搜索分页", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult index(@ModelAttribute E modelAttribute,
                            @ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        Page<E> page = getService().findByCondition(modelAttribute, searchVO, PageUtil.initPage(pageVO));

        return this.success(page);
    }

    @GetMapping("/all")
    @ApiOperation("返回所有数据，需谨慎")
    @BootanLog(value = "返回所有数据", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult all() {

        List<E> models = getService().findAll();
        return this.success(models);
    }

    @PostMapping("/save")
    @ApiOperation("保存")
    @BootanLog(value = "保存", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult save(@ModelAttribute E modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        if (StrUtil.isNotEmpty(request.getParameter("id"))) {
            E model = getService().findById((ID)request.getParameter("id"));
            if (null != model) {

                String[] nullProperties = getNullProperties(modelAttribute);
                BeanUtils.copyProperties(modelAttribute, model, nullProperties);
                model = getService().saveAndFlush(model);
                return this.success(model);
            } else {
                return this.error();
            }
        }

        E model = getService().save(modelAttribute);
        return this.success(model);
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    @BootanLog(value = "批量删除", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult delete(@PathVariable ID[] ids) {

        for (ID id : ids) {
            getService().delete(id);
        }

        if (1 < ids.length) {
            return this.success("批量删除数据成功");
        }
        return this.success();
    }

    @GetMapping("/view/{id}")
    @ApiOperation("查看单个数据详情")
    @BootanLog(value = "查看单个数据详情", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult view(@PathVariable ID id) {

        E model = getService().findById(id);

        if (null == model) {
            return this.error();
        }

        return this.success(model);
    }

    @GetMapping("/search/{keyword}")
    @ApiOperation("按名称搜索")
    @BootanLog(value = "搜索", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult search(@PathVariable String keyword) {

        List<E> models = getService().findByNameOrNameLike(keyword);

        return this.success(new PageContent(models));
    }

    @PostMapping("/enable/{id}")
    @ApiOperation("禁用")
    @BootanLog(value = "禁用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult enable(@PathVariable ID id) {

        E model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_ENABLE);
            getService().save(model);
        }

        return this.success();
    }

    @PostMapping("/disable/{id}")
    @ApiOperation("启用")
    @BootanLog(value = "启用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult disable(@PathVariable ID id) {

        E model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_DISABLE);
            getService().save(model);
        }

        return this.success();
    }

    @PostMapping("/import-data")
    @ApiOperation("导入数据")
    public BaseResult importData(@RequestBody List<E> models) {

        List<String> reasons = new ArrayList<>();
        int i = 0;
        for (E model : models) {
            i++;

            if (null == model.getName()) {
                reasons.add("第" + i + "行：名称为空<br>");
                continue;
            }

            if (null == model.getStatus()) {
                model.setStatus(CommonConstant.STATUS_ENABLE);
            }

            getService().save(model);
        }

        StringBuilder message = new StringBuilder();
        if (0 < reasons.size()) {
            message.append("部分导入成功，成功" + (models.size() - reasons.size()) + "条，失败" + reasons.size() + "条");
            message.append(reasons.toString());
        } else {
            message.append("全部导入成功，共" + (models.size() - reasons.size()) + "条");
        }

        return this.success(message.toString());
    }

    /**
     * 获取对象的空属性
     * @param src 源对象
     * @return String[]
     */
    protected String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }

}
