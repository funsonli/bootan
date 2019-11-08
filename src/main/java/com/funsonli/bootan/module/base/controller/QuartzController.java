package com.funsonli.bootan.module.base.controller;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.Quartz;
import com.funsonli.bootan.module.base.service.QuartzService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Quartz定时任务接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("Quartz定时任务接口")
@RequestMapping("/bootan/quartz")
public class QuartzController extends BaseController<Quartz, String> {

    @Autowired
    private QuartzService modelService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public QuartzService getService() {
        return modelService;
    }

    @Override
    @PostMapping("/save")
    @ApiOperation("保存")
    @BootanLog(value = "保存", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult save(@ModelAttribute Quartz modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        if (StrUtil.isNotEmpty(request.getParameter("id"))) {
            Quartz model = getService().findById((String)request.getParameter("id"));
            if (null != model) {

                String[] nullProperties = getNullProperties(modelAttribute);
                BeanUtils.copyProperties(modelAttribute, model, nullProperties);
                model.setStatus(CommonConstant.STATUS_ENABLE);
                model = getService().saveAndFlush(model);
                deleteJob(model);
                addJob(model);
                return this.success(model);
            } else {
                return this.error();
            }
        }

        Quartz model = getService().save(modelAttribute);
        addJob(model);

        return this.success(model);
    }

    @Override
    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    @BootanLog(value = "批量删除", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult delete(@PathVariable String[] ids) {

        for (String id : ids) {
            Quartz model = getService().findById(id);
            if (model != null) {
                deleteJob(model);
                getService().delete(id);
            }
        }

        if (1 < ids.length) {
            return this.success("批量删除数据成功");
        }
        return this.success();
    }

    @Override
    @PostMapping("/enable/{id}")
    @ApiOperation("启用")
    @BootanLog(value = "启用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult enable(@PathVariable String id) {

        Quartz model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_ENABLE);
            getService().save(model);
            resumeJob(model);
        }

        return this.success();
    }

    @Override
    @PostMapping("/disable/{id}")
    @ApiOperation("禁用")
    @BootanLog(value = "禁用", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult disable(@PathVariable String id) {

        Quartz model = getService().findById(id);
        if (null != model) {
            model.setStatus(CommonConstant.STATUS_DISABLE);
            getService().save(model);
            pauseJob(model);
        }

        return this.success();
    }

    private void addJob(Quartz model) {
        try {
            // 启动调度器
            scheduler.start();

            JobDetail jobDetail = JobBuilder.newJob(getClass(model.getJobClassName()).getClass())
                    .withIdentity(model.getJobClassName())
                    .usingJobData("parameter", model.getParameter())
                    .build();

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(model.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(model.getJobClassName())
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.toString());
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

    private void pauseJob(Quartz model) {
        try {
            scheduler.pauseJob(JobKey.jobKey(model.getJobClassName()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void resumeJob(Quartz model) {
        try {
            scheduler.resumeJob(JobKey.jobKey(model.getJobClassName()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(Quartz model){

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(model.getJobClassName()));
            scheduler.unscheduleJob(TriggerKey.triggerKey(model.getJobClassName()));
            scheduler.deleteJob(JobKey.jobKey(model.getJobClassName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Job getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (Job)clazz.newInstance();
    }

}
