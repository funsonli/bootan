package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Quartz定时任务
 *
 * @author Funsonli
 */
@Data
@Entity
@Table(name = "tbl_quartz")
@TableName("tbl_quartz")
@ApiModel(value = "Quartz定时任务")
public class Quartz extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "定时任务类名")
    private String jobClassName;

    @ApiModelProperty(value = "cron表达式 例：0/3 * * * * ?")
    private String cronExpression;

    @ApiModelProperty(value = "传递参数")
    private String parameter;

    @ApiModelProperty(value = "描述")
    private String description;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
