package com.blog.content.controller;

import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.content.service.ArticleTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result selectArticleTypeList(){
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeList());
    }

    @GetMapping("/tree")
    public Result selectArticleTypeTree(){
        return ResultFactory.buildSuccessResult(articleTypeService.selectArticleTypeTree());
    }

    @PostMapping("/save")
    public Result saveArticleType(@RequestBody ArticleType articleType){
        int flag = articleTypeService.saveArticleType(articleType);
        if (flag == 1) {
            return ResultFactory.buildSuccessResult("文章分类保存成功");
        }
        return ResultFactory.buildSuccessResult("文章分类保存失败");
    }

    @PostMapping("/update")
    public Result updateArticleType(@RequestBody ArticleType articleType){
        int flag = articleTypeService.updateArticleType(articleType);
        if (flag == 1){
            return ResultFactory.buildSuccessResult("文章分类修改成功");
        }
        return ResultFactory.buildSuccessResult("文章分类修改失败，请确认改分类下文章数目是否为0");
    }

    @DeleteMapping("/delete")
    public Result deleteArticleType(@RequestParam(value = "articleTypeId") String articleTypeId){
        return ResultFactory.buildSuccessResult(articleTypeService.deleteArticleTypeById(articleTypeId));
    }
}
