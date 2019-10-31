package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 权限接口
 * @author Funson
 */
public interface PermissionService extends BaseService<Permission, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<Permission> findByCondition(Permission model, SearchVO searchVO, Pageable pageable);

    List<Permission> findByTypeAndStatusOrderBySortOrder(Integer type, Integer status);

    @Override
    List<Permission> findByNameOrNameLike(String keyword);

    List<Permission> findByUserId(String userId);
}
