package com.funsonli.bootan.common.util;

import cn.hutool.core.util.StrUtil;
import com.funsonli.bootan.common.vo.PageVO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页方法
 *
 * @author Funsonli
 * @date 2019/10/31
 */
public class PageUtil {
    public static Pageable initPage(PageVO page){

        Pageable pageable = null;


        int pageNumber = page.getPageNumber() != null ? page.getPageNumber() : 1;
        int pageSize = page.getPageSize() != null ? page.getPageSize() : 10;
        String sort = page.getSort() != null ? page.getSort() : "createdAt";
        String order = page.getOrder() != null ? page.getOrder() : "desc";

        if (pageNumber < 1){
            pageNumber = 1;
        }
        if (pageSize < 1){
            pageSize = 10;
        }
        if(StrUtil.isNotBlank(sort)) {
            Sort.Direction d;
            if(StrUtil.isBlank(order)) {
                d = Sort.Direction.DESC;
            }else {
                d = Sort.Direction.valueOf(order.toUpperCase());
            }
            Sort s = new Sort(d, sort);
            pageable = PageRequest.of(pageNumber - 1, pageSize, s);
        }else {
            pageable = PageRequest.of(pageNumber - 1, pageSize);
        }
        return pageable;
    }

    public static List listToPage(Pageable page, List list) {

        int pageNumber = page.getPageNumber() - 1;
        int pageSize = page.getPageSize();

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }

        int fromIndex = pageNumber * pageSize;
        int toIndex = pageNumber * pageSize + pageSize;

        if (fromIndex > list.size()) {
            return new ArrayList();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

}
