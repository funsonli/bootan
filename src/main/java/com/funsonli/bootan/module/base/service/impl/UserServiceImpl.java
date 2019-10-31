package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.dao.DepartmentDao;
import com.funsonli.bootan.module.base.dao.UserDao;
import com.funsonli.bootan.module.base.entity.Department;
import com.funsonli.bootan.module.base.entity.Permission;
import com.funsonli.bootan.module.base.entity.Role;
import com.funsonli.bootan.module.base.entity.User;
import com.funsonli.bootan.module.base.mapper.PermissionMapper;
import com.funsonli.bootan.module.base.mapper.RoleMapper;
import com.funsonli.bootan.module.base.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class for
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao modelDao;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDao getDao() {
        return modelDao;
    }

    @Override
    public User findByUsername(String username) {
        User user = modelDao.findByUsername(username);
        if (null == user){
            return null;
        }

        if (StrUtil.isNotBlank(user.getDepartmentId())) {
            Department department = departmentDao.findById(user.getDepartmentId()).orElse(null);
            if (null != department) {
                user.setDepartmentName(department.getName());
            }
        }

        List<Role> roles = roleMapper.findByUserId(user.getId());
        user.setRoles(roles);

        List<Permission> permissions = permissionMapper.findByUserId(user.getId());
        user.setPermissions(permissions);

        return user;
    }

    @Override
    public Page<User> findByCondition(User model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<User>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> usernameField = root.get("username");
                Path<String> mobileField = root.get("mobile");
                Path<String> emailField = root.get("email");
                Path<String> departmentIdField = root.get("departmentId");
                Path<String> sexField = root.get("sex");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if(StrUtil.isNotBlank(model.getUsername())){
                    list.add(cb.like(usernameField, '%'+model.getUsername()+'%'));
                }
                if(StrUtil.isNotBlank(model.getMobile())){
                    list.add(cb.like(mobileField, '%'+model.getMobile()+'%'));
                }
                if(StrUtil.isNotBlank(model.getEmail())){
                    list.add(cb.like(emailField, '%'+model.getEmail()+'%'));
                }

                //部门
                if(StrUtil.isNotBlank(model.getDepartmentId())){
                    list.add(cb.equal(departmentIdField, model.getDepartmentId()));
                }

                //性别
                if(StrUtil.isNotBlank(model.getSex())){
                    list.add(cb.equal(sexField, model.getSex()));
                }
                //类型
                if(model.getType()!=null){
                    list.add(cb.equal(typeField, model.getType()));
                }
                //状态
                if(model.getStatus()!=null){
                    list.add(cb.equal(statusField, model.getStatus()));
                }
                //创建时间
                if(StrUtil.isNotBlank(searchVO.getStartDate())&&StrUtil.isNotBlank(searchVO.getEndDate())){
                    Date start = DateUtil.parse(searchVO.getStartDate());
                    Date end = DateUtil.parse(searchVO.getEndDate());
                    list.add(cb.between(createAtField, start, DateUtil.endOfDay(end)));
                }

                //数据权限
                /*List<String> depIds = securityUtil.getDeparmentIds();
                if(depIds!=null&&depIds.size()>0){
                    list.add(departmentIdField.in(depIds));
                }*/

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<User> findByDepartmentId(String departmentId) {
        return modelDao.findByDepartmentIdOrderByCreatedAtAsc(departmentId);
    }

    @Override
    public User findByEmail(String email) {
        return modelDao.findByEmail(email);
    }

    @Override
    public User findByMobile(String mobile) {
        return modelDao.findByMobile(mobile);
    }

}
