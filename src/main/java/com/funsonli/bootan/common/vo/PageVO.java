package com.funsonli.bootan.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页Vo
 *
 * @author Funson
 * @date 2018-06-11
 */
@Data
public class PageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "页号")
    private Integer pageNumber;

    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;

    @ApiModelProperty(value = "排序字段")
    private String sort;

    @ApiModelProperty(value = "排序方式 asc/desc")
    private String order;
}
