package com.funsonli.bootan.module.base.controller;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.Message;
import com.funsonli.bootan.module.base.entity.MessageState;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.MessageService;
import com.funsonli.bootan.module.base.service.MessageStateService;
import com.funsonli.bootan.module.base.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private MessageStateService messageStateService;

    @Autowired
    private UserService userService;

    @Override
    public MessageService getService() {
        return modelService;
    }

    @Override
    @PostMapping("/save")
    @ApiOperation("保存")
    @BootanLog(value = "保存", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult save(@ModelAttribute Message modelAttribute, BindingResult result, HttpServletRequest request, HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        if (StrUtil.isNotEmpty(request.getParameter("id"))) {
            Message model = getService().findById((String)request.getParameter("id"));
            if (null != model) {

                String[] nullProperties = getNullProperties(modelAttribute);
                BeanUtils.copyProperties(modelAttribute, model, nullProperties);
                model = getService().saveAndFlush(model);
                return this.success(model);
            } else {
                return this.error();
            }
        }

        Message model = getService().save(modelAttribute);

        List<MessageState> messageStates = new ArrayList<>();
        if (StrUtil.isEmpty(request.getParameter("id"))) {
            if (modelAttribute.getRange() == 0) {
                List<User> users = userService.findAll();
                users.forEach(user -> {
                    MessageState messageState = new MessageState();
                    messageState.setUserId(user.getId());
                    messageState.setMessageId(model.getId());
                    messageState.setName(model.getName());
                    messageStates.add(messageState);
                });
            } else {
                if (modelAttribute.getUserIds() != null && modelAttribute.getUserIds().length > 0) {
                    for (String userId : modelAttribute.getUserIds()) {
                        MessageState messageState = new MessageState();
                        messageState.setUserId(userId);
                        messageState.setMessageId(model.getId());
                        messageState.setName(model.getName());
                        messageStates.add(messageState);
                    }
                }
            }
        }
        if (messageStates.size() > 0) {
            messageStateService.saveAll(messageStates);
        }

        return this.success(model);
    }


    @Override
    @DeleteMapping("/delete/{ids}")
    @ApiOperation("批量删除")
    @BootanLog(value = "批量删除", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult delete(@PathVariable String[] ids) {

        for (String id : ids) {
            getService().delete(id);
            messageStateService.deleteByMessageId(id);
        }

        if (1 < ids.length) {
            return this.success("批量删除数据成功");
        }
        return this.success();
    }
}
