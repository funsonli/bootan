package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 消息数据处理层
 * @author Funsonli
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
