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
 * 权限项目
 * @author funsonli
 */
@Data
@Entity
@Table(name = "tbl_permission")
@TableName("tbl_permission")
@ApiModel(value = "权限项目")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单标题")
    private String title = "";

    @ApiModelProperty(value = "父id")
    private String parentId = CommonConstant.DEFAULT_PARENT_ID;

    @ApiModelProperty(value = "说明备注")
    private String description = "";

    @ApiModelProperty(value = "前端组件")
    private String component = "";

    @ApiModelProperty(value = "页面路径/资源链接url")
    private String path = "";

    @ApiModelProperty(value = "图标")
    private String icon = "";

    @ApiModelProperty(value = "层级")
    private Integer level = CommonConstant.PERMISSION_LEVEL_0;

    @ApiModelProperty(value = "按钮类型")
    private String buttonType = "";

    @ApiModelProperty(value = "网页链接")
    private String redirectUrl = "";

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "子菜单/权限")
    private List<Permission> children;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "页面拥有的权限类型")
    private List<String> permTypes;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "节点展开 前端用")
    private Boolean expand = true;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否勾选 前端用")
    private Boolean checked = false;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "是否选中 前端用")
    private Boolean selected = false;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
