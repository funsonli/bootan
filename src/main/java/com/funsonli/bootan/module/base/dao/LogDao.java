package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Log;
import org.springframework.stereotype.Repository;

/**
 * 日志数据处理层
 * @author Funson
 */
@Repository
public interface LogDao extends BaseDao<Log, String> {

}
