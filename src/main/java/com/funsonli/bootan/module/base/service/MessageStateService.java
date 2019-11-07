package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.MessageState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 消息发送状态接口
 * @author Funsonli
 */
public interface MessageStateService extends BaseService<MessageState, String> {

    /**
    * 列表搜索分页
    * @param model 对应的实体
    * @param searchVO 搜索
    * @param pageable 分页
    * @return Page<MessageState>
    */
    @Override
    public Page<MessageState> findByCondition(MessageState model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的实体
     * @return <MessageState>
     */
    @Override
    MessageState beforeSave(MessageState entity);

    void deleteByMessageId(String messageId);
}
