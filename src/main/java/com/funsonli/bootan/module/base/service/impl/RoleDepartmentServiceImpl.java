package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.dao.RoleDepartmentDao;
import com.funsonli.bootan.module.base.entity.RoleDepartment;
import com.funsonli.bootan.module.base.mapper.RoleDepartmentMapper;
import com.funsonli.bootan.module.base.service.RoleDepartmentService;
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
 * 角色部门数据范围接口
 * @author Funson
 */
@Service
public class RoleDepartmentServiceImpl implements RoleDepartmentService {

    @Autowired
    private RoleDepartmentDao modelDao;


    @Autowired
    private RoleDepartmentMapper modelMapper;

    @Override
    public RoleDepartmentDao getDao() {
        return modelDao;
    }

    /**
    * 列表搜索分页
    * @param model
    * @param searchVO
    * @param pageable
    * @return
    */
    @Override
    public Page<RoleDepartment> findByCondition(RoleDepartment model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<RoleDepartment>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<RoleDepartment> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                /*if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }*/

                if(model.getType() != null){
                    list.add(cb.equal(typeField, model.getType()));
                }

                if(model.getStatus() != null){
                    list.add(cb.equal(statusField, model.getStatus()));
                }

                if(StrUtil.isNotBlank(searchVO.getStartDate()) && StrUtil.isNotBlank(searchVO.getEndDate())){
                    Date start = DateUtil.parse(searchVO.getStartDate());
                    Date end = DateUtil.parse(searchVO.getEndDate());
                    list.add(cb.between(createAtField, start, DateUtil.endOfDay(end)));
                }

                // 添加您的自定义条件

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public RoleDepartment beforeSave(RoleDepartment entity) {
        if (entity.getRoleId() == null) {
            entity.setRoleId("");
        }
        if (entity.getDepartmentId() == null) {
            entity.setDepartmentId("");
        }
        return entity;
    }

    @Override
    public List<RoleDepartment> findByRoleId(String roleId) {
        return modelDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(String roleId) {
        modelDao.deleteByRoleId(roleId);
    }
}
