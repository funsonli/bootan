package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.RoleDepartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 角色部门数据范围接口
 * @author Funson
 */
public interface RoleDepartmentService extends BaseService<RoleDepartment, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<RoleDepartment> findByCondition(RoleDepartment model, SearchVO searchVO, Pageable pageable);

    List<RoleDepartment> findByRoleId(String roleId);

    void deleteByRoleId(String roleId);
}
