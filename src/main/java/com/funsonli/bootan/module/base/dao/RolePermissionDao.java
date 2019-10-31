package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.RolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色权限数据处理层
 * @author Funson
 */
@Repository
public interface RolePermissionDao extends BaseDao<RolePermission, String> {

    List<RolePermission> findByRoleId(String roleId);

    void deleteByRoleId(String roleId);
}
