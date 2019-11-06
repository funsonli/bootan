package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.MessageState;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 消息发送状态数据处理层
 * @author Funsonli
 */
@Mapper
public interface MessageStateMapper extends BaseMapper<MessageState> {

}
