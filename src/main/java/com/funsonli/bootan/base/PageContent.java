package com.funsonli.bootan.base;

import lombok.Data;

import java.util.List;

/**
 * 带分页的列表，兼容前端iview方式，如果是其他方式请修改该类或者使用其他类
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Data
public class PageContent {
    /**
     * 列表数据
     */
    private List<?> content;

    Boolean empty;
    Boolean first;
    Boolean last;
    Object sort;
    Integer totalElements;
    Integer totalPages;

    Integer number;
    Integer numberOfElements;

    Object pageable;

    public PageContent(List<?> models) {
        this.content = models;
    }
}
