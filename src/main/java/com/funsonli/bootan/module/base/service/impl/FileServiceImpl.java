package com.funsonli.bootan.module.base.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.SearchVO;
import com.funsonli.bootan.module.base.dao.FileDao;
import com.funsonli.bootan.module.base.entity.File;
import com.funsonli.bootan.module.base.mapper.FileMapper;
import com.funsonli.bootan.module.base.service.FileService;
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
 * 文件接口
 * @author Funsonli
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileDao modelDao;


    @Autowired
    private FileMapper modelMapper;

    @Override
    public FileDao getDao() {
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
    public Page<File> findByCondition(File model, SearchVO searchVO, Pageable pageable) {
        return modelDao.findAll(new Specification<File>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<File> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // 默认搜索条件
                Path<String> nameField = root.get("name");
                Path<String> fileKeyField = root.get("fileKey");
                Path<String> contentTypeField = root.get("contentType");
                Path<Integer> typeField = root.get("type");
                Path<Integer> statusField = root.get("status");
                Path<Date> createAtField = root.get("createdAt");
                // 添加您自定义条件

                List<Predicate> list = new ArrayList<Predicate>();

                if(StrUtil.isNotBlank(model.getName())){
                    list.add(cb.like(nameField, '%' + model.getName() + '%'));
                }

                if(StrUtil.isNotBlank(model.getFileKey())){
                    list.add(cb.like(fileKeyField, '%' + model.getFileKey() + '%'));
                }

                if(StrUtil.isNotBlank(model.getContentType())){
                    list.add(cb.like(contentTypeField, '%' + model.getContentType() + '%'));
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
    public File beforeSave(File entity) {

        // 添加未在BaseEntity中定义的字段
        if (entity.getSize() == null) {
            entity.setSize(0L);
        }
        if (entity.getUrl() == null) {
            entity.setUrl("");
        }
        if (entity.getFileKey() == null) {
            entity.setFileKey("");
        }
        if (entity.getContentType() == null) {
            entity.setContentType("");
        }
        if (entity.getLocation() == null) {
            entity.setLocation(0);
        }

        return entity;
    }
}
