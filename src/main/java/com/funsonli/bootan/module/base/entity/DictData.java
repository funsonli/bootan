package com.funsonli.bootan.module.base.entity;

import com.funsonli.bootan.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 字典数据
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
@Entity
@Table(name = "tbl_dict_data")
@TableName("tbl_dict_data")
@ApiModel(value = "字典数据")
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "字典ID")
    private String dictId;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
