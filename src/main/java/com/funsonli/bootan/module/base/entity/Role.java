package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import com.funsonli.bootan.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 角色
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_role")
@TableName("tbl_role")
@ApiModel(value = "角色")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名 以ROLE_开头")
    private String name;

    @ApiModelProperty(value = "可访问的部门数据权限")
    private Integer departmentType = CommonConstant.ROLE_DEPARTMENT_TYPE;

    @ApiModelProperty(value = "备注")
    private String description;


    @ApiModelProperty(value = "可访问的部门数据权限")
    private Integer isDefault = CommonConstant.ROLE_DEFAULT_NO;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有权限")
    private List<RolePermission> permissions;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "拥有数据权限")
    private List<RoleDepartment> departments;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
