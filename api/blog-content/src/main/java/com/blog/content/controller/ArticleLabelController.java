package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.ArticleLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author: lxk
 * @date: 2022/6/15 18:54
 * @description: 文章标签接口
 * @modified By:
 */

@Slf4j
@RestController
@RequestMapping("/article/label")
public class ArticleLabelController {


    @Autowired
    private ArticleLabelService articleLabelService;

    @GetMapping("/list")
    public Result selectArticleLabelList(@RequestParam(value = "labelType") Integer labelType){
        return ResultFactory.buildSuccessResult(articleLabelService.selectArticleLabelList(labelType));
    }

    @PostMapping("/save")
    public Result saveArticleLabel(@RequestHeader HttpHeaders headers, @RequestBody ArticleLabel articleLabel) {
        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            articleLabel.setUserId(blogUser.getId());
            articleLabelService.saveArticleLabel(articleLabel);
            return ResultFactory.buildSuccessResult("文章标签保存成功");
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildSuccessResult("文章标签保存失败");
    }

    @PostMapping("/update")
    public Result updateArticleLabel(@RequestBody ArticleLabel articleLabel) {
        int flag = articleLabelService.updateArticleLabel(articleLabel);
        if (flag == 1) {
            return ResultFactory.buildSuccessResult("文章标签修改成功");
        }
        return ResultFactory.buildSuccessResult("文章标签修改失败");
    }

    @DeleteMapping("/delete")
    public Result deleteArticleLabelByIds(@RequestHeader HttpHeaders headers, @RequestParam(value = "ids") String ids) {

        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            return ResultFactory.buildSuccessResult(articleLabelService.deleteArticleLabelByIds(ids, blogUser.getId()));
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildSuccessResult("文章标签删除失败");
    }


}
