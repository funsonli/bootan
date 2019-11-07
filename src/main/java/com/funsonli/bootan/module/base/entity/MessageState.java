package com.funsonli.bootan.module.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.funsonli.bootan.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 消息发送状态
 *
 * @author Funsonli
 */
@Data
@Entity
@Table(name = "tbl_message_state")
@TableName("tbl_message_state")
@ApiModel(value = "消息发送状态")
public class MessageState extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String messageId;

    private String userId;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "接收用户名")
    private String username;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "消息标题")
    private String title;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "消息内容")
    private String content;

    @Transient
    @TableField(exist = false)
    @ApiModelProperty(value = "消息类型")
    private Integer type;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
