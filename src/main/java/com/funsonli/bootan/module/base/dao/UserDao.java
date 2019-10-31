package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Repository
public interface UserDao extends BaseDao<User, String> {
    /**
     * 通过用户名获取用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    List<User> findByDepartmentIdOrderByCreatedAtAsc(String departmentId);

    User findByEmail(String email);

    User findByMobile(String mobile);
}
