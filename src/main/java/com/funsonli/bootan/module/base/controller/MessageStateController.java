package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.module.base.entity.MessageState;
import com.funsonli.bootan.module.base.service.MessageStateService;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息发送状态接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("消息发送状态接口")
@RequestMapping("/bootan/message-state")
public class MessageStateController extends BaseController<MessageState, String> {

    @Autowired
    private MessageStateService modelService;

    @Override
    public BaseService getService() {
        return modelService;
    }

}
