package com.funsonli.bootan.module.base.entity;

import com.funsonli.bootan.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_dict")
@TableName("tbl_dict")
@ApiModel(value = "字典")
public class Dict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "标题")
    private String title;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
