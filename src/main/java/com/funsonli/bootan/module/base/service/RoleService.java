package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色接口
 * @author Funson
 */
public interface RoleService extends BaseService<Role, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<Role> findByCondition(Role model, SearchVO searchVO, Pageable pageable);

    List<Role> findByUserId(String userId);

    void updateIsDefaultNo();

    Role findByIsDefault(Integer isDefault);
}
