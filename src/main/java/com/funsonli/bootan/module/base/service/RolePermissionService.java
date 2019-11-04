package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.RolePermission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色权限接口
 * @author Funson
 */
public interface RolePermissionService extends BaseService<RolePermission, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<RolePermission> findByCondition(RolePermission model, SearchVO searchVO, Pageable pageable);

    @Override
    RolePermission beforeSave(RolePermission entity);

    List<RolePermission> findByRoleId(String roleId);

    void deleteByRoleId(String roleId);

}
