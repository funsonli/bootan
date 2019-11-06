package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 文件
 *
 * @author Funsonli
 */
@Data
@Entity
@Table(name = "tbl_file")
@TableName("tbl_file")
@ApiModel(value = "文件")
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long size;

    private String url;

    private String fileKey;

    private String contentType;

    private Integer location;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
