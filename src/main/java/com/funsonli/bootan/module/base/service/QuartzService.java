package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Quartz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Quartz定时任务接口
 * @author Funsonli
 */
public interface QuartzService extends BaseService<Quartz, String> {

    /**
    * 列表搜索分页
    * @param model 对应的实体
    * @param searchVO 搜索
    * @param pageable 分页
    * @return Page<Quartz>
    */
    @Override
    public Page<Quartz> findByCondition(Quartz model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的实体
     * @return <Quartz>
     */
    @Override
    Quartz beforeSave(Quartz entity);

}
