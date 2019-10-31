package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 部门接口
 * @author Funson
 */
public interface DepartmentService extends BaseService<Department, String> {

    /**
    * 列表搜索分页
    * @param model 对应的model
    * @param searchVO 搜索字符串
    * @param pageable 分页
    * @return Page<Department>
    */
    @Override
    Page<Department> findByCondition(Department model, SearchVO searchVO, Pageable pageable);

    @Override
    List<Department> findByNameOrNameLike(String keyword);

    List<Department> findByParentId(String parentId);

    List<Department> findMapperByParentId(String parentId);

}
