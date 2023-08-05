package com.blog.file.controller;

import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.FileService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;

/**
 * @description: 文件服务接口类
 * @Author: 308501
 * @date 2023/8/2 14:59
 */

@Slf4j
@RestController
@RequestMapping("/dir")
public class FileController {

    @Resource
    private FileService fileService;

    @PostMapping("/save")
    public Result saveFileDir(@RequestHeader HttpHeaders headers,@Validated @RequestBody FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.saveFileDir(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    @DeleteMapping("/delete")
    public Result deleteFileOrDir(@RequestHeader HttpHeaders headers,@Validated @RequestBody FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.deleteFileOrDir(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    @PostMapping("/update")
    public Result updateFileOrDirName(@RequestHeader HttpHeaders headers,@Validated @RequestBody FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.updateFileOrDirName(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    @GetMapping("/select")
    public Result selectFileDir(@RequestHeader HttpHeaders headers,@Validated FileDataVo fileDataVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.selectFileDir(blogUser, fileDataVo));
    }

    @GetMapping("/space")
    public Result selectUserSpace(@RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.selectUserSpace(blogUser));
    }

}
