package com.funsonli.bootan.module.activiti.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 模型管理
 *
 * @author Funsonli
 */
@Data
@Entity
@Table(name = "tbl_act_model")
@TableName("tbl_act_model")
@ApiModel(value = "模型管理")
public class ActModel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模型key")
    private String modelKey;

    @ApiModelProperty(value = "模型版本")
    private Integer version;

    @ApiModelProperty(value = "描述")
    private String description;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
