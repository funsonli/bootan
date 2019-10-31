package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.DictData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典数据数据处理层
 * @author Funson
 */
@Repository
public interface DictDataDao extends BaseDao<DictData, String> {
    List<DictData> findByDictIdOrderBySortOrderAsc(String dictId);

}
