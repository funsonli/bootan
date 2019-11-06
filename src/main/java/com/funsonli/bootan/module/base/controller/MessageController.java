package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.Message;
import com.funsonli.bootan.module.base.service.MessageService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("消息接口")
@RequestMapping("/bootan/message")
public class MessageController extends BaseController<Message, String> {

    @Autowired
    private MessageService modelService;

    @Override
    public BaseService getService() {
        return modelService;
    }

}
