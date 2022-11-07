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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
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
    public Result selectArticleByPage(ArticleVo articleVo) {
        Integer userId = articleVo.getUserId();
        Integer page = articleVo.getPageNum();
        Integer size = articleVo.getPageSize();
        
        if (userId == 0){
            log.info("分页查询全部文章， page: {},  size: {}", page, size);
            return ResultFactory.buildSuccessResult(articleService.selectArticleListByPage(articleVo));
        } else {
            // 标记文章列表是否属于当前用户
            boolean isOwner = false;
            HashMap<String, Object> map = new HashMap<>();
            // 获取用户请求头，从请求头中获取token
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter("Authorization");
            }
            MyPage<ArticleVo> result = articleService.selectArticleListByPageAndUserId(articleVo);
            if (token != null && !token.isEmpty()){
                try {
                    BlogUser blogUser = JwtUtil.getUserInfo(token);
                    if (blogUser.getId().equals(userId)) {
                        isOwner = true;
                    }
                    log.info("用户: {} 分页查询用户id为: {} 的全部文章， page: {},  size: {}", blogUser.getUsername(), userId, page, size);
                    map.put("isOwner", isOwner);
                    map.put("list", result);
                    return ResultFactory.buildSuccessResult(map);
                } catch (Exception e){
                    log.warn(Constant.JWTError, e);
                }
            }
            log.info("分页查询用户id为: {} 全部文章， page: {},  size: {}", userId, page, size);
            map.put("isOwner", isOwner);
            map.put("list", result);
            return ResultFactory.buildSuccessResult(map);
        }
    }

    @GetMapping("/id")
    public Result selectArticleById(@RequestParam(value = "id") Integer articleId){
        return ResultFactory.buildSuccessResult(articleService.selectArticleById(articleId));
    }

    @PostMapping("/save")
    public Result saveArticle(@RequestBody Article article){
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()){
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                article.setUserId(blogUser.getId());
                int result = articleService.saveArticle(article);
                if (result == 1){
                    return ResultFactory.buildSuccessResult();
                }
            } catch (Exception e){
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleSaveFail);
    }

    @PostMapping("/update")
    public Result updateArticle(@RequestBody ArticleVo articleVo){
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()){
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                int code = articleService.updateArticle(blogUser, articleVo);
                if (code == 1){
                    return ResultFactory.buildSuccessResult();
                }
            } catch (Exception e){
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleUpdateFail);
    }

    @DeleteMapping("/delete")
    public Result deleteArticle(@RequestParam(value = "articleIds") String articleIds){
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()){
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                return ResultFactory.buildSuccessResult(articleService.deleteArticle(blogUser, articleIds));
            } catch (Exception e){
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult(Constant.articleUpdateFail);
    }

}
