package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

/**
 * 字典数据处理层
 * @author Funson
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}
