package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限数据处理层
 * @author Funson
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}
