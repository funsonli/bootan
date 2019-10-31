package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.RoleDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色部门数据范围数据处理层
 * @author Funson
 */
@Repository
public interface RoleDepartmentDao extends BaseDao<RoleDepartment, String> {

    List<RoleDepartment> findByRoleId(String roleId);

    void deleteByRoleId(String roleId);
}
