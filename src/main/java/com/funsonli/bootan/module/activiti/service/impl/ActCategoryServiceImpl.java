package com.funsonli.bootan.module.activiti.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.activiti.entity.ActCategory;
import com.funsonli.bootan.module.activiti.dao.ActCategoryDao;
import com.funsonli.bootan.module.activiti.mapper.ActCategoryMapper;
import com.funsonli.bootan.module.activiti.service.ActCategoryService;
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
 * 工作流分类接口
 * @author Funsonli
 */
@Service
public class ActCategoryServiceImpl implements ActCategoryService {

    @Autowired
    private ActCategoryDao modelDao;


    @Autowired
    private ActCategoryMapper modelMapper;

    @Override
    public ActCategoryDao getDao() {
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
    public Page<ActCategory> findByCondition(ActCategory model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<ActCategory>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<ActCategory> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }

                if(null != model.getType()){
                    list.add(cb.equal(typeField, model.getType()));
                }

                if(null != model.getStatus()){
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
    public ActCategory beforeSave(ActCategory entity) {

        // 添加未在BaseEntity中定义的字段
        /*if (entity.getName1() == null) {
            entity.setName1("");
        }*/

        return entity;
    }
}
