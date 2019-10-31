package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import com.funsonli.bootan.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 部门实体类
 * 
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_department")
@TableName("tbl_department")
@ApiModel(value = "部门")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父id")
    private String parentId = CommonConstant.DEFAULT_PARENT_ID;

    @ApiModelProperty(value = "父名称")
    private String parentName = "";

    @ApiModelProperty(value = "是否有子节点")
    private Boolean isParent = false;

    @ApiModelProperty(value = "备注")
    private String description = "";

    @ApiModelProperty(value = "主负责人")
    private String head = "";

    @ApiModelProperty(value = "副负责人")
    private String viceHead = "";

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
