package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.MessageState;
import org.springframework.stereotype.Repository;

/**
 * 消息发送状态数据处理层
 * @author Funsonli
 */
@Repository
public interface MessageStateDao extends BaseDao<MessageState, String> {

    void deleteByMessageId(String messageId);
}
