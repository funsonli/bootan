package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.File;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 文件接口
 * @author Funsonli
 */
public interface FileService extends BaseService<File, String> {

    /**
    * 列表搜索分页
     * @param model 对应的实体
     * @param searchVO 搜索
     * @param pageable 分页
    * @return Page<File>
    */
    @Override
    public Page<File> findByCondition(File model, SearchVO searchVO, Pageable pageable);

    /**
     * 保存前设置默认值
     * @param entity 对应的model
     * @return <File>
     */
    @Override
    File beforeSave(File entity);

}
