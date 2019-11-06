package com.funsonli.bootan.module.base.entity;

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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
