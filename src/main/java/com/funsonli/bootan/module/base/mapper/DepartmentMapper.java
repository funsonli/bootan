package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Department;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门数据处理层
 * @author Funson
 */
@Mapper
public interface DepartmentMapper extends BaseMapper<Department> {

}
