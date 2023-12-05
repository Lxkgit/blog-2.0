package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.common.util.MyPage;
import com.blog.content.feign.UserClient;
import com.blog.content.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/8 20:34
 * @description: 文章服务接口
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:article:insert')")
    public Result saveArticle(@RequestHeader HttpHeaders headers, @RequestBody Article article) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        article.setUserId(blogUser.getId());
        return ResultFactory.buildSuccessResult(articleService.saveArticle(article));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:article:delete')")
    public Result deleteArticle(@RequestHeader HttpHeaders headers, @RequestParam(value = "articleIds") String articleIds) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(articleService.deleteArticle(blogUser, articleIds));
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:article:update')")
    public Result updateArticle(@RequestHeader HttpHeaders headers, @RequestBody ArticleVo articleVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(articleService.updateArticle(blogUser, articleVo));
    }

    @GetMapping("/list")
    public Result selectArticleByPage(@RequestHeader HttpHeaders headers, ArticleVo articleVo) {
        BlogUser blogUser;
        String token = String.valueOf(headers.get("Authorization"));
        if (token != null && !token.equals("") && !token.equals("null")) {
            blogUser = JwtUtil.getUserInfo(token);
            if (articleVo.getType() != null && articleVo.getType() == 1) {
                articleVo.setUserId(blogUser.getId());
                articleVo.setBlogUser(blogUser);
            }
        }
        MyPage<ArticleVo> result = articleService.selectArticleListByPageAndUserId(articleVo);
        return ResultFactory.buildSuccessResult(result);
    }

    @GetMapping("/id")
    public Result selectArticleById(@RequestParam(value = "id") Integer articleId) {
        return ResultFactory.buildSuccessResult(articleService.selectArticleById(articleId));
    }

}
