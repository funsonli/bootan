package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 日志接口
 * @author Funson
 */
public interface LogService extends BaseService<Log, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<Log> findByCondition(Log model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的model
     * @return Log
     */
    @Override
    Log beforeSave(Log entity);
}
