package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DocContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/21 16:38
 * @description: 文档内容接口
 */

@Slf4j
@RestController
@RequestMapping("/doc/content")
public class DocContentController {

    @Autowired
    private DocContentService docContentService;

    @GetMapping("/id")
    public Result getDocContentById(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        return ResultFactory.buildSuccessResult(docContentService.selectDocContentByCatalogId(id));
    }

    @PostMapping("/save")
    public Result saveDocContent(@RequestBody DocContent docContent) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            docContent.setUserId(blogUser.getId());
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        int flag = docContentService.saveDocContent(docContent);
        if (flag == 1){
            return ResultFactory.buildSuccessResult("保存成功 ... ");
        }
        return ResultFactory.buildFailResult("保存失败 ... ");
    }

    @PostMapping("/update")
    public Result updateDocContent(@RequestBody DocContent docContent) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            docContent.setUserId(blogUser.getId());
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        int flag = docContentService.updateDocContent(docContent);
        if (flag == 1){
            return ResultFactory.buildSuccessResult("修改成功 ... ");
        }
        return ResultFactory.buildFailResult("修改失败 ... ");
    }

}
