package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: lxk
 * @date 2022/6/14 16:30
 * @description: 文章类型控制类
 */

@Slf4j
@RestController
@RequestMapping("/article/type")
public class ArticleTypeController {

    @Autowired
    private ArticleTypeService articleTypeService;

    @GetMapping("/list")
    public Result selectArticleTypeList() {
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeList());
    }

    @GetMapping("/node")
    public Result selectArticleTypeListByParentId(@RequestParam(value = "parentId") String parentId) {
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeByParentId(parentId));
    }

    @GetMapping("/id")
    public Result selectArticleTypeById(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeById(id));
    }

    @GetMapping("/tree")
    public Result selectArticleTypeTree() {
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeTree());
    }

    @PostMapping("/save")
    public Result saveArticleType(@RequestHeader HttpHeaders headers, @RequestBody ArticleType articleType) {
        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            articleType.setCreateUser(blogUser.getId());
            articleTypeService.saveArticleType(articleType);
            return ResultFactory.buildSuccessResult("文章分类保存成功");
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildSuccessResult("文章分类保存失败");
    }

    @PostMapping("/update")
    public Result updateArticleType(@RequestHeader HttpHeaders headers, @RequestBody ArticleType articleType) {

        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            articleType.setCreateUser(blogUser.getId());
            articleTypeService.updateArticleType(articleType);
            int flag = articleTypeService.updateArticleType(articleType);
            if (flag == 1) {
                return ResultFactory.buildSuccessResult("文章分类修改成功");
            }
            return ResultFactory.buildSuccessResult("文章分类修改失败，请确认改分类下文章数目是否为0");

        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("文章分类修改失败");
    }

    @DeleteMapping("/delete")
    public Result deleteArticleType(@RequestParam(value = "articleTypeId") String articleTypeId) {
        return ResultFactory.buildSuccessResult(articleTypeService.deleteArticleTypeById(articleTypeId));
    }
}
