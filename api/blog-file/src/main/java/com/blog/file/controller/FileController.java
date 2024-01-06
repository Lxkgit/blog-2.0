package com.blog.file.controller;

import com.blog.common.entity.file.vo.FileDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 文件服务接口类
 * @Author: lxk
 * @date 2023/8/2 14:59
 */

@Slf4j
@RestController
@RequestMapping("/dir")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 创建云盘目录
     *
     * @param headers
     * @param fileDataVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:file:user:save')")
    public Result saveFileDir(@RequestHeader HttpHeaders headers, @Validated @RequestBody FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.saveFileDir(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    /**
     * 删除云盘文件或目录
     *
     * @param headers
     * @param fileDataVo
     * @return
     * @throws ValidException
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:file:user:delete')")
    public Result deleteFileOrDir(@RequestHeader HttpHeaders headers, @Validated FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.deleteFileOrDir(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    /**
     * 修改云盘文件或目录名称
     *
     * @param headers
     * @param fileDataVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:file:user:update')")
    public Result updateFileOrDirName(@RequestHeader HttpHeaders headers, @Validated @RequestBody FileDataVo fileDataVo) throws ValidException {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        fileService.updateFileOrDirName(blogUser, fileDataVo);
        return ResultFactory.buildSuccessResult();
    }

    /**
     * 查看文件列表
     *
     * @param headers
     * @param fileDataVo
     * @return
     */
    @GetMapping("/select")
    @PreAuthorize("hasAnyAuthority('sys:file:user:select')")
    public Result selectFileDir(@RequestHeader HttpHeaders headers, @Validated FileDataVo fileDataVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.selectFileDir(blogUser, fileDataVo));
    }

    /**
     * 获取云盘剩余空间大小
     *
     * @param headers
     * @return
     */
    @GetMapping("/space")
    @PreAuthorize("hasAnyAuthority('sys:file:user:space')")
    public Result selectUserSpace(@RequestHeader HttpHeaders headers) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.selectUserSpace(blogUser));
    }

    /**
     * 同步文件(包括文件同步至远程和从远程下载文件)
     *
     * @param headers
     * @param fileDataVo
     * @return
     */
    @GetMapping("/sync")
    @PreAuthorize("hasAnyAuthority('sys:file:user:sync')")
    public Result syncFile(@RequestHeader HttpHeaders headers, FileDataVo fileDataVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(fileService.syncFile(blogUser, fileDataVo));
    }

}
