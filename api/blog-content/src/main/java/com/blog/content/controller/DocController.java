package com.blog.content.controller;

import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.content.doc.DocContent;
import com.blog.common.entity.content.doc.vo.DocCatalogVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.DocService;
import lombok.extern.slf4j.Slf4j;
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

    @PutMapping("/content/insert")
    public Result insertDocCatalog(@RequestHeader HttpHeaders headers, DocCatalog docCatalog) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.insertDonCatalog(blogUser, docCatalog));
    }

    @DeleteMapping("/content/delete")
    public Result deleteDocContent(@RequestHeader HttpHeaders headers, @RequestParam(value = "id") Integer id) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.deleteDocCatalog(blogUser, id));
    }

    @PostMapping("/content/update")
    public Result updateDocContent(@RequestHeader HttpHeaders headers, DocContent docContent) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.updateDocContent(blogUser, docContent));
    }

    @PostMapping("/catalog/update")
    public Result updateDocCatalog(@RequestHeader HttpHeaders headers, DocCatalog docCatalog){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.updateDocCatalog(blogUser, docCatalog));
    }

    @GetMapping("/catalog/tree")
    public Result selectDocCatalogTree(DocCatalogVo docCatalogVo) {
        return ResultFactory.buildSuccessResult(docService.selectDocCatalogTree(docCatalogVo));
    }

    @GetMapping("/content/id")
    public Result selectDocContentById(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(docService.selectDocContentById(id));
    }

}