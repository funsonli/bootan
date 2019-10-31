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
 * 用户角色
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_user_role")
@TableName("tbl_user_role")
@ApiModel(value = "用户角色")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private String userId = "";

    @ApiModelProperty(value = "角色id")
    private String roleId = "";

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}