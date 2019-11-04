package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户角色接口
 * @author Funson
 */
public interface UserRoleService extends BaseService<UserRole, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<UserRole> findByCondition(UserRole model, SearchVO searchVO, Pageable pageable);

    @Override
    UserRole beforeSave(UserRole entity);

    List<UserRole> findByUserId(String userId);

    void deleteByUserId(String userId);

}
