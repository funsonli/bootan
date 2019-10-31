package com.funsonli.bootan.module.base.service;

import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
public interface UserService extends BaseService<User, String> {

    /**
     * 列表搜索分页
     * @param model 对应的model
     * @param searchVO 搜索字符串
     * @param pageable 分页
     * @return Page<Department>
     */
    @Override
    Page<User> findByCondition(User model, SearchVO searchVO, Pageable pageable);

    User findByUsername(String username);

    List<User> findByDepartmentId(String departmentId);

    User findByEmail(String email);

    User findByMobile(String mobile);

}
