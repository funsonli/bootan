package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 用户
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@Data
@Entity
@Table(name = "tbl_user")
@TableName("tbl_user")
@ApiModel(value = "用户")
public class User extends BaseEntity {

    @ApiModelProperty(value = "用户名")
    @Column(unique = true, nullable = false)
    private String username = "";

    @ApiModelProperty(value = "密码")
    @Column(nullable = false)
    private String password = "";

    @ApiModelProperty(value = "邮箱")
    private String email = "";

    @ApiModelProperty(value = "昵称")
    private String nickName = "";

    @ApiModelProperty(value = "头像")
    private String avatar = "";

    @ApiModelProperty(value = "描述")
    private String description = "";

    @ApiModelProperty(value = "性别")
    private String sex = "";

    @ApiModelProperty(value = "地址")
    private String address = "";

    @ApiModelProperty(value = "手机号")
    private String mobile = "";

    @ApiModelProperty(value = "备注")
    private String remark = "";

    @ApiModelProperty(value = "部门id")
    private String departmentId = "";

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "部门名称")
    private String departmentName;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有角色")
    private List<Role> roles;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "用户拥有的权限")
    private List<Permission> permissions;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
