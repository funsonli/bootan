package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseController;
import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.common.annotation.BootanLog;
import com.funsonli.bootan.common.constant.CommonConstant;
import com.funsonli.bootan.module.base.entity.File;
import com.funsonli.bootan.module.base.service.FileService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件接口
 *
 * @author Funsonli
 * @date 2019/10/31
 */
@Slf4j
@RestController
@Transactional(rollbackFor = RuntimeException.class)
@ApiModel("文件接口")
@RequestMapping("/bootan/file")
public class FileController extends BaseController<File, String> {

    @Autowired
    private FileService modelService;

    @Override
    public FileService getService() {
        return modelService;
    }

    @PostMapping({"/copy"})
    @ApiOperation("复制")
    @BootanLog(value = "复制", type = CommonConstant.LOG_TYPE_OPERATION)
    public BaseResult copy() {
        return BaseResult.success();
    }
}
