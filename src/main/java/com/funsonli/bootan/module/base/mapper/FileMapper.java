package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.File;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文件数据处理层
 * @author Funsonli
 */
@Mapper
public interface FileMapper extends BaseMapper<File> {

}
