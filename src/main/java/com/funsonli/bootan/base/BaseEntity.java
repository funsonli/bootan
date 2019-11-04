package com.funsonli.bootan.base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * 以下为每个Bootan表都有的字段，Name可以忽略
 * 每个字段都有值这样数据表可以设计成NOT NULL DEFAULT ""，便于数据库提升性能
 * Date 2019-08-16
 * @author Funsonli
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableId
    @ApiModelProperty(value = "唯一主键ID | Unique primary key ID")
    private String id;

    @NotFound(action= NotFoundAction.IGNORE)
    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "记录状态 | Record Status")
    private Integer type;

    @ApiModelProperty(value = "排序值 | Record sort order")
    private Integer sortOrder;

    @ApiModelProperty(value = "状态 | Record sort order")
    private Integer status;

    @CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间 | Record created time")
    private Date createdAt;

    @LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间 | Record updated time")
    private Date updatedAt;

    @ApiModelProperty(value = "创建者 | Record Created by")
    private String createdBy;

    @ApiModelProperty(value = "更新者 | Record updated by")
    private String updatedBy;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
