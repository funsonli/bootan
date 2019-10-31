package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限数据处理层
 * @author Funson
 */
@Repository
public interface PermissionDao extends BaseDao<Permission,String> {

    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    List<Permission> findByNameOrNameLikeOrderBySortOrderAsc(String keyword, String keyword1);
}
