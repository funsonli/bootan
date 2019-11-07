package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.module.base.entity.Log;
import com.funsonli.bootan.module.base.service.LogService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("日志接口")
@RequestMapping("/bootan/log")
public class LogController extends BaseController<Log, String> {

    @Autowired
    private LogService modelService;

    @Override
    public LogService getService() {
        return modelService;
    }

}
