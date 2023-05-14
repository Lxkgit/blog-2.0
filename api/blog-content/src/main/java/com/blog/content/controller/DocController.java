package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DocService;
import com.blog.content.service.DocContentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lxk
 * @date 2022/6/20 9:24
 * @description:
 */

@Slf4j
@RestController
@RequestMapping("/doc")
public class DocController {

    @Autowired
    private DocService docService;

    @Autowired
    private DocContentService docContentService;

    @GetMapping("/catalog/tree")
    public Result selectDocCatalogTree(DocCatalogVo docCatalogVo) {
        return ResultFactory.buildSuccessResult(docService.selectDocCatalogTree(docCatalogVo));
    }



    @GetMapping("/list")
    public Result selectDocCatalogList(@RequestHeader HttpHeaders headers, DocCatalogVo docCatalogVo) {
        if (docCatalogVo.getSelectType().equals("admin")) {
            String token = String.valueOf(headers.get("Authorization"));
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            docCatalogVo.setUserId(blogUser.getId());
        } else if (docCatalogVo.getSelectType().equals("home")) {
            if (docCatalogVo.getUserId() != null) {
                docCatalogVo.setUserId(0);
            }
        }
        return ResultFactory.buildSuccessResult(docService.selectDocCatalogTree(docCatalogVo));
    }

    @GetMapping("/id")
    public Result selectDocCatalogByParentId(@RequestHeader HttpHeaders headers, DocCatalogVo docCatalogVo) {
        if(docCatalogVo.getType() == 0) {
            String token = String.valueOf(headers.get("Authorization"));
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            docCatalogVo.setUserId(blogUser.getId());
        }
        return ResultFactory.buildSuccessResult(docService.selectDocCatalogListById(docCatalogVo));
    }

    @PostMapping("/save")
    public Result saveDocCatalog(@RequestHeader HttpHeaders headers, @RequestBody DocCatalog catalog) {
        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            catalog.setUserId(blogUser.getId());
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildSuccessResult(docService.saveDoc(catalog));
    }

    @PostMapping("/update")
    public Result updateDocCatalog(@RequestHeader HttpHeaders headers, @RequestBody DocCatalog catalog) {
        try {
            String token = String.valueOf(headers.get("Authorization"));
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            catalog.setUserId(blogUser.getId());
            return ResultFactory.buildSuccessResult(docService.updateDocCatalog(catalog));
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("修改失败 ... ");
    }

    @DeleteMapping("/delete")
    public Result deleteDoc(@RequestHeader HttpHeaders headers, @RequestParam(value = "ids") String ids) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.deleteDocCatalogByIds(ids, blogUser.getId()));
    }

    @GetMapping("/select/content")
    public Result getDocContent(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(docContentService.selectDocContentByCatalogId(id));
    }

    @PostMapping("/update/content")
    public Result updateDocContent(@RequestHeader HttpHeaders headers, @RequestBody DocContent docContent) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        docContent.setUserId(blogUser.getId());
        int flag = docContentService.updateDocContent(docContent);
        if (flag == 1) {
            return ResultFactory.buildSuccessResult("文档修改成功 ... ");
        } else {
            return ResultFactory.buildFailResult("文档保存失败 ... ");
        }
    }



}