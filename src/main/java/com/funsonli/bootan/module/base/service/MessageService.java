package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 消息接口
 * @author Funsonli
 */
public interface MessageService extends BaseService<Message, String> {

    /**
    * 列表搜索分页
    * @param model 对应的实体
    * @param searchVO 搜索
    * @param pageable 分页
    * @return Page<Message>
    */
    @Override
    public Page<Message> findByCondition(Message model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的实体
     * @return <Message>
     */
    @Override
    Message beforeSave(Message entity);

}
