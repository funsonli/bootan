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
 * @author Funson
 */
@Data
@Entity
@Table(name = "tbl_log")
@TableName("tbl_log")
@ApiModel(value = "日志")
public class Log extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日志类型")
    private Integer type = CommonConstant.LOG_TYPE_ACCESS;

    @ApiModelProperty(value = "请求路径")
    private String requestUrl = "";

    @ApiModelProperty(value = "请求类型")
    private String requestType = "";

    @ApiModelProperty(value = "请求参数")
    private String requestParam = "";

    @ApiModelProperty(value = "请求用户")
    private String username = "";

    @ApiModelProperty(value = "ip")
    private String ip = "";

    @ApiModelProperty(value = "ip信息")
    private String ipInfo = "";

    @ApiModelProperty(value = "花费时间")
    private Integer costTime = 0;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
