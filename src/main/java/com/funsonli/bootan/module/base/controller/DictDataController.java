package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.module.base.entity.Dict;
import com.funsonli.bootan.module.base.entity.DictData;
import com.funsonli.bootan.module.base.service.DictDataService;
import com.funsonli.bootan.module.base.service.DictService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典数据接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("字典数据接口")
@RequestMapping("/bootan/dict-data")
public class DictDataController extends BaseController<DictData, String> {

    @Autowired
    private DictDataService modelService;

    @Autowired
    private DictService dictService;

    @Override
    public DictDataService getService() {
        return modelService;
    }

    @GetMapping("/view-name/{name}")
    @ApiOperation("查看单个数据详情")
    public BaseResult viewName(@PathVariable String name) {

        Dict dict = dictService.findByName(name);
        List<DictData> models = modelService.findByDictId(dict.getId());

        return BaseResult.success(models);
    }
}
