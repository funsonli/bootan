package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.base.BaseService;
import com.funsonli.bootan.base.PageContent;
import com.funsonli.bootan.module.base.entity.Dict;
import com.funsonli.bootan.module.base.service.DictService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 字典接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("字典接口")
@RequestMapping("/bootan/dict")
public class DictController extends BaseController<Dict, String> {

    @Autowired
    private DictService modelService;

    @Override
    public BaseService getService() {
        return modelService;
    }

    @Override
    @GetMapping("/all")
    @ApiOperation("返回所有数据，需谨慎")
    public BaseResult all() {
        List<Dict> models = modelService.findAllBySortOrder();

        return BaseResult.success(new PageContent(models));
    }

    @Override
    @GetMapping("/search/{keyword}")
    @ApiOperation("按名称搜索")
    public BaseResult search(@PathVariable String keyword) {
        List<Dict> models = modelService.findAllByTitleOrTitleLike(keyword);

        return BaseResult.success(new PageContent(models));
    }
}
