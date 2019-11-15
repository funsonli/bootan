package com.funsonli.bootan.module.activiti.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.activiti.entity.ActModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 模型管理接口
 * @author Funsonli
 */
public interface ActModelService extends BaseService<ActModel, String> {

    /**
    * 列表搜索分页
    * @param model 对应的实体
    * @param searchVO 搜索
    * @param pageable 分页
    * @return Page<ActModel>
    */
    @Override
    public Page<ActModel> findByCondition(ActModel model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的实体
     * @return <ActModel>
     */
    @Override
    ActModel beforeSave(ActModel entity);

}
