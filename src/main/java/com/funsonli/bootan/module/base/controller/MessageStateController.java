package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.PageUtil;
import com.funsonli.bootan.common.vo.PageVO;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Message;
import com.funsonli.bootan.module.base.entity.MessageState;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.service.MessageService;
import com.funsonli.bootan.module.base.service.MessageStateService;
import com.funsonli.bootan.module.base.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Override
    public MessageStateService getService() {
        return modelService;
    }

    @Override
    @GetMapping({"/", "index"})
    @ApiOperation("数据范围列表搜索分页")
    @BootanLog(value = "数据范围列表搜索分页", type = CommonConstant.LOG_TYPE_ACCESS)
    public BaseResult index(@ModelAttribute MessageState modelAttribute,
                            @ModelAttribute SearchVO searchVO,
                            @ModelAttribute PageVO pageVO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            return this.error();
        }

        Page<MessageState> page = getService().findByCondition(modelAttribute, searchVO, PageUtil.initPage(pageVO));

        if (page.getContent() != null && page.getContent().size() > 0) {
            page.getContent().forEach(item -> {
                User u = userService.findById(item.getUserId());
                if (u != null) {
                    item.setUsername(u.getUsername());
                }
                Message m = messageService.findById(item.getMessageId());
                if (m != null) {
                    item.setName(m.getName());
                    item.setContent(m.getContent());
                    item.setType(m.getType());
                }
            });
        }

        return this.success(page);
    }
}
