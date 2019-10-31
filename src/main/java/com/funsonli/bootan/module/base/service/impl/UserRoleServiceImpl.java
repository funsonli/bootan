package com.funsonli.bootan.module.base.service.impl;

import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.dao.UserRoleDao;
import com.funsonli.bootan.module.base.entity.UserRole;
import com.funsonli.bootan.module.base.mapper.UserRoleMapper;
import com.funsonli.bootan.module.base.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色接口
 * @author Funson
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao modelDao;

    @Autowired
    private UserRoleMapper modelMapper;

    @Override
    public UserRoleDao getDao() {
        return modelDao;
    }

    @Override
    public Page<UserRole> findByCondition(UserRole model, SearchVO searchVO, Pageable pageable) {
        return null;
    }

    public List<UserRole> findByUserId(String userId) {
        return modelDao.findByUserId(userId);
    }

    public void deleteByUserId(String userId) {
        modelDao.deleteByUserId(userId);
    }

}
