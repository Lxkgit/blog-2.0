package com.blog.content.controller;

import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.common.util.MyPage;
import com.blog.common.valication.group.*;
import com.blog.content.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lxk
 * @date 2022/6/8 20:34
 * @description: 文章服务接口
 */

@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {

    @Resource
    private ArticleService articleService;

    /**
     * 创建文章
     *
     * @param request
     * @param articleVo
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:article:insert')")
    public Result saveArticle(HttpServletRequest request, @RequestBody @Validated(value = {AddGroup.class}) ArticleVo articleVo) throws ValidException {
        articleVo.setUserId(getBlogUser(request).getId());
        return ResultFactory.buildSuccessResult(articleService.saveArticle(articleVo));
    }

    /**
     * 删除文章
     *
     * @param request
     * @param articleVo
     * @return
     * @throws ValidException
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:article:delete')")
    public Result deleteArticle(HttpServletRequest request, @Validated(value = {DeleteGroup.class}) ArticleVo articleVo) throws ValidException {
        return ResultFactory.buildSuccessResult(articleService.deleteArticle(getBlogUser(request), articleVo.getArticleIds()));
    }

    /**
     * 修改文章
     *
     * @param request
     * @param articleVo
     * @return
     * @throws ValidException
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:article:update')")
    public Result updateArticle(HttpServletRequest request, @RequestBody @Validated(value = {UpdateGroup.class}) ArticleVo articleVo) throws ValidException {
        BlogUser blogUser = getBlogUser(request);
        return ResultFactory.buildSuccessResult(articleService.updateArticle(blogUser, articleVo));
    }

    /**
     * 分页查询文章
     *
     * @param headers
     * @param articleVo
     * @return
     * @throws ValidException
     */
    @GetMapping("/list")
    public Result selectArticleByPage(@RequestHeader HttpHeaders headers, @Validated(value = {SelectListGroup.class}) ArticleVo articleVo) throws ValidException {
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

    /**
     * 指定id查询文章
     *
     * @param articleVo
     * @return
     */
    @GetMapping("/id")
    public Result selectArticleById(@Validated(value = {SelectIdGroup.class}) ArticleVo articleVo) {
        return ResultFactory.buildSuccessResult(articleService.selectArticleById(articleVo.getId()));
    }

}
