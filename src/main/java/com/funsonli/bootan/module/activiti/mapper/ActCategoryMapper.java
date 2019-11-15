package com.funsonli.bootan.module.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.activiti.entity.ActCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 工作流分类数据处理层
 * @author Funsonli
 */
@Mapper
public interface ActCategoryMapper extends BaseMapper<ActCategory> {

}
