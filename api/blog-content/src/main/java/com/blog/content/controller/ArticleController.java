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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result selectArticleByPage(@RequestHeader HttpHeaders headers, ArticleVo articleVo) {
        BlogUser blogUser;
        try {
            String token = String.valueOf(headers.get("Authorization"));
            if (token != null && !token.equals("") && !token.equals("null")) {
                blogUser = JwtUtil.getUserInfo(token);
                if (articleVo.getType() != null && articleVo.getType().equals("admin")) {
                    articleVo.setUserId(blogUser.getId());
                }
            }
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        MyPage<ArticleVo> result = articleService.selectArticleListByPageAndUserId(articleVo);
        return ResultFactory.buildSuccessResult(result);
    }

    @GetMapping("/id")
    public Result selectArticleById(@RequestParam(value = "id") Integer articleId) {
        return ResultFactory.buildSuccessResult(articleService.selectArticleById(articleId));
    }

    @PostMapping("/save")
    public Result saveArticle(@RequestBody Article article) {
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()) {
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                article.setUserId(blogUser.getId());
                int result = articleService.saveArticle(article);
                if (result == 1) {
                    return ResultFactory.buildSuccessResult(article.getId());
                }
            } catch (Exception e) {
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleSaveFail);
    }

    @PostMapping("/update")
    public Result updateArticle(@RequestBody ArticleVo articleVo) {
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()) {
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                int code = articleService.updateArticle(blogUser, articleVo);
                if (code == 1) {
                    return ResultFactory.buildSuccessResult();
                }
            } catch (Exception e) {
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleUpdateFail);
    }

    @DeleteMapping("/delete")
    public Result deleteArticle(@RequestParam(value = "articleIds") String articleIds) {
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()) {
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                return ResultFactory.buildSuccessResult(articleService.deleteArticle(blogUser, articleIds));
            } catch (Exception e) {
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleUpdateFail);
    }

}
