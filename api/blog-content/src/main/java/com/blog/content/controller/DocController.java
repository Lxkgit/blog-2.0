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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('sys:doc:insert')")
//    @RequestHeader(required = false,value = "Authorization")
    public Result insertDocCatalog(@RequestHeader HttpHeaders headers,@RequestBody DocCatalog docCatalog) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.insertDocCatalog(blogUser, docCatalog));
    }

    @DeleteMapping("/content/delete")
    @PreAuthorize("hasAnyAuthority('sys:doc:delete')")
    public Result deleteDocContent(@RequestHeader HttpHeaders headers, @RequestParam(value = "id") Integer id) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.deleteDocCatalog(blogUser, id));
    }

    @PostMapping("/content/update")
    @PreAuthorize("hasAnyAuthority('sys:doc:update')")
    public Result updateDocContent(@RequestHeader HttpHeaders headers,@RequestBody DocContent docContent) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.updateDocContent(blogUser, docContent));
    }

    @PostMapping("/catalog/update")
    @PreAuthorize("hasAnyAuthority('sys:doc:catalog:update')")
    public Result updateDocCatalog(@RequestHeader HttpHeaders headers,@RequestBody DocCatalog docCatalog){
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(docService.updateDocCatalog(blogUser, docCatalog));
    }

    @GetMapping("/catalog/tree")
    public Result selectDocCatalogTree(@RequestHeader HttpHeaders headers, DocCatalogVo docCatalogVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = null;
        if (token != null && (token.contains("bearer") || token.contains("Bearer"))) {
            blogUser = JwtUtil.getUserInfo(token);
        }
        return ResultFactory.buildSuccessResult(docService.selectDocCatalogTree(blogUser, docCatalogVo));
    }

    @GetMapping("/content/id")
    public Result selectDocContentById(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(docService.selectDocContentById(id));
    }

    @GetMapping("/content/user")
    public  Result selectDocUserList() {
        return ResultFactory.buildSuccessResult(docService.selectDocUserList());
    }

}