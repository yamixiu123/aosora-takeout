package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AwsBucketUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Api(tags ="通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AwsBucketUtil awsBucketUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传{}",file);
        try {
            log.info("file name is {}",file.getName());
           String accessUrl =  awsBucketUtil.upload(file.getBytes(), file.getOriginalFilename());
           return Result.success(accessUrl);
        } catch (IOException e) {
            log.error("MessageConstant.UPLOAD_FAILED:{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);

    }
}
