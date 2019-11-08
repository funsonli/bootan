package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Quartz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Quartz定时任务数据处理层
 * @author Funsonli
 */
@Mapper
public interface QuartzMapper extends BaseMapper<Quartz> {

}
