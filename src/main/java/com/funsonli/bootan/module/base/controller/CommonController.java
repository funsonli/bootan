package com.funsonli.bootan.module.base.controller;

import com.funsonli.bootan.base.BaseResult;
import com.funsonli.bootan.component.uploader.UploaderFactory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@ApiModel("学生接口")
@RequestMapping("/bootan/common")
public class CommonController {
    @Autowired
    private UploaderFactory uploaderFactory;

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ApiOperation(value = "文件上传")
    public BaseResult upload(@RequestParam(required = false) MultipartFile file,
                             HttpServletRequest request) {

        String result = null;
        String fileName = rename(file.getOriginalFilename());
        try {
            InputStream inputStream = file.getInputStream();
            result = uploaderFactory.getUploader().uploadInputStream(inputStream, fileName);
        } catch (Exception e) {
            log.error(e.toString());
            return BaseResult.error(e.toString());
        }

        return BaseResult.success(result);
    }

    /**
     * 以UUID重命名
     *
     * @param fileName 文件名
     * @return String
     */
    private String rename(String fileName) {
        String extName = fileName.substring(fileName.lastIndexOf("."));
        return UUID.randomUUID().toString().replace("-", "") + extName;
    }
}
