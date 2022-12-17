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
    public Result selectArticleLabelList(@RequestParam(required = false, value = "userId", defaultValue = "0") Integer userId){

        if (userId == 0){
            return ResultFactory.buildSuccessResult(articleLabelService.selectArticleLabelList(userId));
        } else {
            // 标记标签列表是否属于当前用户
            boolean isOwner = false;
            HashMap<String, Object> map = new HashMap<>();
            // 获取用户请求头，从请求头中获取token
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader("Authorization");
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter("Authorization");
            }
            List<ArticleLabel> result = articleLabelService.selectArticleLabelList(userId);
            if (!token.isEmpty()) {
                try {
                    BlogUser blogUser = JwtUtil.getUserInfo(token);
                    if (blogUser.getId().equals(userId)) {
                        isOwner = true;
                    }
                    map.put("isOwner", isOwner);
                    map.put("list", result);
                    return ResultFactory.buildSuccessResult(map);
                } catch (Exception e) {
                    log.warn(Constant.JWTError, e);
                }
            }
            map.put("isOwner", isOwner);
            map.put("list", result);
            return ResultFactory.buildSuccessResult(map);
        }
    }

    @PostMapping("/save")
    public Result saveArticleLabel(@RequestBody ArticleLabel articleLabel) {

        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()) {
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                articleLabel.setUserId(blogUser.getId());
                int flag = articleLabelService.saveArticleLabel(articleLabel);
                if (flag == 1) {
                    return ResultFactory.buildSuccessResult("文章标签保存成功");
                }
                return ResultFactory.buildSuccessResult("文章标签保存失败");
            } catch (Exception e) {
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult("应该用不到 ... ");
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
    public Result deleteArticleLabelByIds(@RequestParam(value = "ids") String ids) {

        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        if (!token.isEmpty()) {
            try {
                BlogUser blogUser = JwtUtil.getUserInfo(token);
                return ResultFactory.buildSuccessResult(articleLabelService.deleteArticleLabelByIds(ids, blogUser.getId()));
            } catch (Exception e) {
                log.warn(Constant.JWTError, e);
            }
        }
        return ResultFactory.buildFailResult("应该用不到 ... ");
    }


}
