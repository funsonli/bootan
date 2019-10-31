package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Dict;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典数据处理层
 * @author Funson
 */
@Repository
public interface DictDao extends BaseDao<Dict, String> {
    List<Dict> findAllByOrderBySortOrderAscCreatedAtDesc();

    List<Dict> findByTitleOrTitleLikeOrderBySortOrderAsc(String keyword, String keyword1);

    Dict findByName(String name);

}
