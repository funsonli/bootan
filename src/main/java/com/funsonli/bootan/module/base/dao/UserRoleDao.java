package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Repository
public interface UserRoleDao extends BaseDao<UserRole, String> {
    List<UserRole> findByUserId(String userId);

    void deleteByUserId(String userId);
}
