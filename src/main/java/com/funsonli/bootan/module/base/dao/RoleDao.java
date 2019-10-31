package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Repository
public interface RoleDao extends BaseDao<Role, String> {

    @Modifying
    @Query(value = "update role set is_default = 0", nativeQuery = true)
    void updateIsDefaultNo();

    Role findByIsDefault(Integer isDefault);
}
