package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.dao.DictDataDao;
import com.funsonli.bootan.module.base.entity.DictData;
import com.funsonli.bootan.module.base.mapper.DictDataMapper;
import com.funsonli.bootan.module.base.service.DictDataService;
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
 * 字典数据接口
 * @author Funson
 */
@Service
public class DictDataServiceImpl implements DictDataService {

    @Autowired
    private DictDataDao modelDao;


    @Autowired
    private DictDataMapper modelMapper;

    @Override
    public DictDataDao getDao() {
        return modelDao;
    }

    @Override
    public Page<DictData> findByCondition(DictData model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<DictData>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<DictData> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<String> dictIdField = root.get("dictId");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }

                if(StrUtil.isNotBlank(model.getDictId())){
                    list.add(cb.equal(dictIdField, model.getDictId()));
                }

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
    public DictData beforeSave(DictData entity) {
        if (entity.getValue() == null) {
            entity.setValue("");
        }
        if (entity.getDictId() == null) {
            entity.setDictId("");
        }
        return entity;
    }

    @Override
    public List<DictData> findByDictId(String dictId) {
        return modelDao.findByDictIdOrderBySortOrderAsc(dictId);
    }
}
