package com.funsonli.bootan.module.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.funsonli.bootan.module.base.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findByUserId(@Param("userId") String userId);

}
