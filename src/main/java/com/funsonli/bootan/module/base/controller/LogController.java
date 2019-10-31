package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.Log;
import com.funsonli.bootan.module.base.service.LogService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@RestController
@Transactional
@ApiModel("日志接口")
@RequestMapping("/bootan/log")
public class LogController extends BaseController<Log, String> {

    @Autowired
    private LogService modelService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BaseService getService() {
        return modelService;
    }

}
