package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.Quartz;
import org.springframework.stereotype.Repository;

/**
 * Quartz定时任务数据处理层
 * @author Funsonli
 */
@Repository
public interface QuartzDao extends BaseDao<Quartz, String> {

}
