package com.blog.content.controller;

import com.blog.common.entity.content.vo.ArticleVo;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.MyPage;
import com.blog.content.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/list/{page}/{size}")
    public Result selectArticleByPage(@PathVariable("page") int page,@PathVariable("size") int size){
        log.info("无权限分页查询文章， page: {},  size: {}", page, size);
        return ResultFactory.buildSuccessResult(articleService.selectArticleListByPage(page, size));
    }

    @GetMapping("/list/user/{page}/{size}")
    public Result selectArticleByPageAndUser(Principal principal,HttpServletRequest request, @PathVariable("page") int page, @PathVariable("size") int size){
        System.out.println(principal);
        System.out.println(request.getHeader("Authorization"));
        MyPage<ArticleVo> result = articleService.selectArticleListByPageAndUserId(null, page, size);
        if (result.getList().size() > 0){
            log.info("用户{}， 分页查询文章， page: {},  size: {}", result.getList().get(0).getBlogUser().getUsername(), page, size);
        }
        return ResultFactory.buildSuccessResult(result);
    }

    @GetMapping("/list")
    public Result selectArticleListAll(){
        return ResultFactory.buildSuccessResult(articleService.selectArticleListAll());
    }

}
