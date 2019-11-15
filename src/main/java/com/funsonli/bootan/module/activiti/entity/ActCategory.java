package com.funsonli.bootan.module.activiti.entity;

import com.funsonli.bootan.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 工作流分类
 *
 * @author Funsonli
 */
@Data
@Entity
@Table(name = "tbl_act_category")
@TableName("tbl_act_category")
@ApiModel(value = "工作流分类")
public class ActCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
