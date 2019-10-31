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
 * 角色数据关联部门
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_role_department")
@TableName("tbl_role_department")
@ApiModel(value = "角色数据关联部门")
public class RoleDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "部门id")
    private String departmentId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}