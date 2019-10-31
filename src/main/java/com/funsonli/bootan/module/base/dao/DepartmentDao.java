package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门数据处理层
 * @author Funson
 */
@Repository
public interface DepartmentDao extends BaseDao<Department, String> {

    /**
     * 根据父ID排序获取
     * @author Funsonli
     * @date 2019/10/31
     * @param parentId 父ID
     * @return List<Department>
     */
    List<Department> findByParentIdOrderBySortOrderAsc(String parentId);

    /**
     * 搜索
     * @author Funsonli
     * @date 2019/10/31
     * @param keyword 搜索字符串
     * @param keyword1 搜索字符串
     * @return List<Department>
     */
    @Override
    List<Department> findByNameOrNameLikeOrderBySortOrderAsc(String keyword, String keyword1);
}
