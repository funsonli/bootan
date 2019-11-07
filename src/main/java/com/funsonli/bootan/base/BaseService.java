package com.funsonli.bootan.base;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.common.util.SnowFlake;
import com.funsonli.bootan.common.vo.SearchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基础Service，包含一些通用的方法
 *
 * @author Funsonli
 * @date 2019/10/31
 */
public interface BaseService<E extends BaseEntity, ID extends Serializable> {
    @Autowired
    BaseDao<E, ID> getDao();

    E beforeSave(E entity);

    default E save(E entity) {
        entity = beforeSaveDefault(entity);
        entity = beforeSave(entity);
        return getDao().save(entity);
    }

    default Iterable<E> saveAll(Iterable<E> entities) {
        for (E e : entities) {
            e = beforeSaveDefault(e);
            e = beforeSave(e);
        }
        return getDao().saveAll(entities);
    }

    default E beforeSaveDefault(E entity) {
        if (entity.getId() == null) {
            entity.setId(String.valueOf(SnowFlake.getInstance().nextId()));
        }
        if (entity.getName() == null) {
            entity.setName("");
        }
        if (entity.getType() == null) {
            entity.setType(CommonConstant.TYPE_DEFAULT);
        }
        if (entity.getSortOrder() == null) {
            entity.setSortOrder(CommonConstant.SORT_ORDER_DEFAULT);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(CommonConstant.STATUS_ENABLE);
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(new Date());
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(new Date());
        }
        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy("");
        }
        if (entity.getUpdatedBy() == null) {
            entity.setUpdatedBy("");
        }
        return entity;
    }

    default E saveAndFlush(E entity) {

        return getDao().saveAndFlush(entity);
    }

    default E update(E entity) {

        return getDao().saveAndFlush(entity);
    }

    default void delete(ID id) {
        if (getDao().existsById(id)) {
            getDao().deleteById(id);
        }
    }

    default Page<E> findByCondition(E model, SearchVO searchVO, Pageable pageable) {
        return getDao().findAll(new Specification<E>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<E> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
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

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    default List<E> findByNameOrNameLike(String keyword) {
        return getDao().findByNameOrNameLikeOrderBySortOrderAsc(keyword, "%" + keyword + "%");
    }

    default void delete(E entity) {
        getDao().delete(entity);
    }

    default long count() {
        return getDao().count();
    }

    default long count(Specification<E> specification) {
        return getDao().count(specification);
    }

    default List<E> findAll() {
        return getDao().findAll();
    }

    default List<E> findAllByCreatedAt() {
        return getDao().findAllByOrderByCreatedAtDesc();
    }

    default List<E> findAllBySortOrder() {
        return getDao().findAllByOrderBySortOrderAscCreatedAtDesc();
    }

    default E findById(ID id) {
        return getDao().findById(id).orElse(null);
    }

    default Page<E> findAll(Pageable pageable){
        return getDao().findAll(pageable);
    }

    default Page<E> findAll(Specification<E> specification, Pageable pageable) {
        return getDao().findAll(specification, pageable);
    }

    default void flush() {
        getDao().flush();
    }
}
