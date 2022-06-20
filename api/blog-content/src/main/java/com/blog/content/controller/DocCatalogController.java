package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DocCatalogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/20 9:24
 * @description:
 */

@Slf4j
@RestController
@RequestMapping("/doc/catalog")
public class DocCatalogController {

    @Autowired
    private DocCatalogService docCatalogService;

    @GetMapping("/list")
    public Result getDocCatalogList() {

        return ResultFactory.buildSuccessResult(docCatalogService.selectDocCatalogList());
    }

    @GetMapping("/tree")
    public Result getDocCatalogTree(@RequestParam(required = false, value = "treeNode", defaultValue = "0") Integer treeNode) {
        return ResultFactory.buildSuccessResult(docCatalogService.selectDocCatalogTree(treeNode));
    }

    @PostMapping("/save")
    public Result saveDocCatalog(@RequestBody DocCatalog catalog){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            catalog.setUserId(blogUser.getId());
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        int flag = docCatalogService.saveDocCatalog(catalog);
        if (flag == 1){
            return ResultFactory.buildSuccessResult("保存成功 ... ");
        }
        return ResultFactory.buildFailResult("保存失败 ... ");
    }

    @PostMapping("/update")
    public Result updateDocCatalog(@RequestBody DocCatalog catalog){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            catalog.setUserId(blogUser.getId());
            int flag = docCatalogService.updateDocCatalog(catalog);
            if (flag == 1){
                return ResultFactory.buildSuccessResult("修改成功 ... ");
            }
            return ResultFactory.buildFailResult("修改失败 ... ");
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("修改失败 ... ");

    }

    @DeleteMapping("/delete")
    public Result deleteDocCatalog(@RequestParam(value = "docCatalogIds") String docCatalogIds){
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        Map<String, Object> map;
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            map = docCatalogService.deleteDocCatalogByIds(docCatalogIds, blogUser.getId());
            return ResultFactory.buildSuccessResult(map);
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("删除失败 ... ");
    }

}