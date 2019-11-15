package com.funsonli.bootan.module.activiti.controller;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.PageContent;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.activiti.entity.ActModel;
import com.funsonli.bootan.module.activiti.service.ActModelService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模型管理接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("模型管理接口")
@RequestMapping("/bootan/act-model")
public class ActModelController extends BaseController<ActModel, String> {

    @Autowired
    private ActModelService modelService;

    @Override
    public ActModelService getService() {
        return modelService;
    }

    @Override
    @GetMapping({"/", "index"})
    @ApiOperation("数据范围列表搜索分页")
    @BootanLog(value = "数据范围列表搜索分页", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult index(@ModelAttribute ActModel modelAttribute,
                            @ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        Page<ActModel> page = getService().findByCondition(modelAttribute, searchVO, PageUtil.initPage(pageVO));

        return this.success(page);
    }

    @Override
    @GetMapping("/all")
    @ApiOperation("返回所有数据，需谨慎")
    @BootanLog(value = "返回所有数据", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult all() {

        List<ActModel> models = getService().findAllBySortOrder();
        return this.success(models);
    }

    @Override
    @PostMapping("/create")
    @ApiOperation("创建")
    @BootanLog(value = "创建", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult create(@ModelAttribute ActModel modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelAttribute.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        String description = modelAttribute.getDescription();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(modelAttribute.getName());
        modelData.setKey(modelAttribute.getModelKey());

        try {
            //保存模型
            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
            return this.error("创建模型失败");
        }
        modelAttribute.setId(modelData.getId());
        modelAttribute.setVersion(modelAttribute.getVersion());
        ActModel model = getService().save(modelAttribute);
        return this.success(model);
    }

    @Override
    @PostMapping("/update")
    @ApiOperation("更新")
    @BootanLog(value = "更新", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult update(@ModelAttribute ActModel modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        if (StrUtil.isNotEmpty(request.getParameter("id"))) {
            ActModel model = getService().findById((String)request.getParameter("id"));
            if (null != model) {

                String[] nullProperties = getNullProperties(modelAttribute);
                BeanUtils.copyProperties(modelAttribute, model, nullProperties);
                model = getService().saveAndFlush(model);
                return this.success(model);
            } else {
                return this.error();
            }
        } else {
            return this.error();
        }
    }

    @Override
    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    @BootanLog(value = "批量删除", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult delete(@PathVariable String[] ids) {

        for (String id : ids) {
            getService().delete(id);
        }

        if (1 < ids.length) {
            return this.success("批量删除数据成功");
        }
        return this.success();
    }

    @Override
    @GetMapping("/view/{id}")
    @ApiOperation("查看单个数据详情")
    @BootanLog(value = "查看单个数据详情", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult view(@PathVariable String id) {

        ActModel model = getService().findById(id);

        if (null == model) {
            return this.error();
        }

        return this.success(model);
    }

    @Override
    @GetMapping("/search/{keyword}")
    @ApiOperation("按名称搜索")
    @BootanLog(value = "搜索", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult search(@PathVariable String keyword) {

        List<ActModel> models = getService().findByNameOrNameLike(keyword);

        return this.success(new PageContent(models));
    }

    @Override
    @PostMapping("/enable/{id}")
    @ApiOperation("禁用")
    @BootanLog(value = "禁用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult enable(@PathVariable String id) {

        ActModel model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_ENABLE);
            getService().save(model);
        }

        return this.success();
    }

    @Override
    @PostMapping("/disable/{id}")
    @ApiOperation("启用")
    @BootanLog(value = "启用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult disable(@PathVariable String id) {

        ActModel model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_DISABLE);
            getService().save(model);
        }

        return this.success();
    }

    @Override
    @PostMapping("/import-data")
    @ApiOperation("导入数据")
    public BaseResult importData(@RequestBody List<ActModel> models) {

        List<String> reasons = new ArrayList<>();
        int i = 0;
        for (ActModel model : models) {
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

}
