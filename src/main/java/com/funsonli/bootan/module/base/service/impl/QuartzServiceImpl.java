package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.entity.Quartz;
import com.funsonli.bootan.module.base.dao.QuartzDao;
import com.funsonli.bootan.module.base.mapper.QuartzMapper;
import com.funsonli.bootan.module.base.service.QuartzService;
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
 * Quartz定时任务接口
 * @author Funsonli
 */
@Service
public class QuartzServiceImpl implements QuartzService {

    @Autowired
    private QuartzDao modelDao;


    @Autowired
    private QuartzMapper modelMapper;

    @Override
    public QuartzDao getDao() {
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
    public Page<Quartz> findByCondition(Quartz model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<Quartz>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Quartz> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<String> jobClassNameField = root.get("jobClassName");
                Path<String> descriptionField = root.get("description");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }

                if(StrUtil.isNotBlank(model.getJobClassName())){
                    list.add(cb.like(jobClassNameField, '%' + model.getJobClassName() + '%'));
                }

                if(StrUtil.isNotBlank(model.getDescription())){
                    list.add(cb.like(descriptionField, '%' + model.getDescription() + '%'));
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
    public Quartz beforeSave(Quartz entity) {

        // 添加未在BaseEntity中定义的字段
        if (entity.getJobClassName() == null) {
            entity.setJobClassName("");
        }
        if (entity.getCronExpression() == null) {
            entity.setCronExpression("");
        }
        if (entity.getParameter() == null) {
            entity.setParameter("");
        }
        if (entity.getDescription() == null) {
            entity.setDescription("");
        }

        return entity;
    }
}
