package com.blog.file.controller;

import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.file.service.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: lxk
 * @date 2022/7/7 15:42
 * @description: 文件上传接口服务
 */

@Slf4j
@RestController
@RequestMapping("/files")
public class UploadFileController {

    @Autowired
    private UploadFileService fileUploadService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile[] files, String type) throws IOException {
        if (files == null || files.length == 0) {
            return ResultFactory.buildFailResult("文件上传失败 ... ");
        }
        fileUploadService.uploadFileList(files, type);
        return null;
    }
}
