package com.blog.file.controller;

import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @description: 文件服务接口类
 * @Author: 308501
 * @date 2023/8/2 14:59
 */

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private FileService fileService;

    @GetMapping("/select/dir")
    public Result selectFileDir(@RequestHeader HttpHeaders headers, @RequestParam(value = "filePath") String filePath) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.selectFileDir(blogUser, filePath));
    }

}
