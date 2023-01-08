package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DocCatalogService;
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
 * @date 2022/6/20 9:24
 * @description:
 */

@Slf4j
@RestController
@RequestMapping("/doc/catalog")
public class DocController {

    @Autowired
    private DocCatalogService docCatalogService;

    @Autowired
    private DocContentService docContentService;

    @GetMapping("/list")
    public Result selectDocCatalogByPage(DocCatalogVo docCatalogVo) {
        return ResultFactory.buildSuccessResult(docCatalogService.selectDocCatalogListByPage(docCatalogVo));
    }

    @GetMapping("/id")
    public Result selectDocCatalogByParentId(@RequestParam(value = "id") Integer parentId) {
        return ResultFactory.buildSuccessResult(docCatalogService.selectDocCatalogListById(parentId));
    }

    @GetMapping("/select/content")
    public Result getDocContent(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(docContentService.selectDocContentByCatalogId(id));
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
        return ResultFactory.buildSuccessResult(docCatalogService.saveDoc(catalog));
    }

    @PostMapping("/save/content")
    public Result saveDocContent(@RequestBody DocContent docContent){
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
            return ResultFactory.buildSuccessResult("文档报错成功 ... ");
        } else {
            return ResultFactory.buildFailResult("文档保存失败 ... ");
        }

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
            return ResultFactory.buildSuccessResult(docCatalogService.updateDocCatalog(catalog));
        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("修改失败 ... ");

    }

}