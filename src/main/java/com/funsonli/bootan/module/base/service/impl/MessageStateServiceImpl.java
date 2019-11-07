package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.MessageState;
import com.funsonli.bootan.module.base.dao.MessageStateDao;
import com.funsonli.bootan.module.base.mapper.MessageStateMapper;
import com.funsonli.bootan.module.base.service.MessageStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息发送状态接口
 * @author Funsonli
 */
@Service
public class MessageStateServiceImpl implements MessageStateService {

    @Autowired
    private MessageStateDao modelDao;


    @Autowired
    private MessageStateMapper modelMapper;

    @Override
    public MessageStateDao getDao() {
        return modelDao;
    }

    /**
    * 列表搜索分页
    * @param model
    * @param searchVO
    * @param pageable
    * @return
    */
    @Override
    public Page<MessageState> findByCondition(MessageState model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<MessageState>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<MessageState> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<String> messageIdField = root.get("messageId");
                Path<String> userIdField = root.get("userId");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }

                if(StrUtil.isNotBlank(model.getMessageId())){
                    list.add(cb.like(messageIdField, '%' + model.getMessageId() + '%'));
                }

                if(StrUtil.isNotBlank(model.getUserId())){
                    list.add(cb.like(userIdField, '%' + model.getUserId() + '%'));
                }

                if(null != model.getType()){
                    list.add(cb.equal(typeField, model.getType()));
                }

                if(null != model.getStatus()){
                    list.add(cb.equal(statusField, model.getStatus()));
                }

                if(StrUtil.isNotBlank(searchVO.getStartDate()) && StrUtil.isNotBlank(searchVO.getEndDate())){
                    Date start = DateUtil.parse(searchVO.getStartDate());
                    Date end = DateUtil.parse(searchVO.getEndDate());
                    list.add(cb.between(createAtField, start, DateUtil.endOfDay(end)));
                }

                // 添加您的自定义条件

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public MessageState beforeSave(MessageState entity) {

        // 添加未在BaseEntity中定义的字段
        if (entity.getMessageId() == null) {
            entity.setMessageId("");
        }
        if (entity.getUserId() == null) {
            entity.setUserId("");
        }

        return entity;
    }

    @Override
    public void deleteByMessageId(String messageId) {
        getDao().deleteByMessageId(messageId);
    }
}
