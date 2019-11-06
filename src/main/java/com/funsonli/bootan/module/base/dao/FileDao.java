package com.funsonli.bootan.module.base.dao;

import com.funsonli.bootan.base.BaseDao;
import com.funsonli.bootan.module.base.entity.File;
import org.springframework.stereotype.Repository;

/**
 * 文件数据处理层
 * @author Funsonli
 */
@Repository
public interface FileDao extends BaseDao<File, String> {

}
