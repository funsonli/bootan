package com.funsonli.bootan.module.activiti.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.activiti.entity.ActCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 工作流分类接口
 * @author Funsonli
 */
public interface ActCategoryService extends BaseService<ActCategory, String> {

    /**
    * 列表搜索分页
    * @param model 对应的实体
    * @param searchVO 搜索
    * @param pageable 分页
    * @return Page<ActCategory>
    */
    @Override
    public Page<ActCategory> findByCondition(ActCategory model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的实体
     * @return <ActCategory>
     */
    @Override
    ActCategory beforeSave(ActCategory entity);

}
