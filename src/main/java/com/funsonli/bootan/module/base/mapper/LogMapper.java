package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Log;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志数据处理层
 * @author Funson
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

}
