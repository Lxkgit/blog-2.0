package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.ArticleLabelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: lxk
 * @date 2022/6/17 14:24
 * @description: 文章标签分类接口
 */

@Slf4j
@RestController
@RequestMapping("/label/type")
public class ArticleLabelTypeController {

    @Autowired
    private ArticleLabelTypeService articleLabelTypeService;

    @GetMapping("/list")
    public Result getArticleLabelTypeList() {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.getArticleLabelTypeList());
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:insert')")
    public Result saveArticleLabelType(@RequestHeader HttpHeaders headers, @RequestBody ArticleLabelType articleLabelType) {

        String token = String.valueOf(headers.get("Authorization"));
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            articleLabelType.setUserId(blogUser.getId());
            articleLabelTypeService.saveArticleLabelType(articleLabelType);
            return ResultFactory.buildSuccessResult("标签分类保存成功 ... ");
        } catch (Exception e) {
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("标签分类保存失败 ... ");
    }

    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:update')")
    public Result updateArticleLabelType(@RequestBody ArticleLabelType articleLabelType) {
        int flag = articleLabelTypeService.updateArticleLabelType(articleLabelType);
        if (flag == 1) {
            return ResultFactory.buildSuccessResult("标签分类修改成功 ... ");
        }
        return ResultFactory.buildFailResult("标签分类修改失败 ... ");
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:delete')")
    public Result deleteArticleLabelTypeByIds(@RequestParam(value = "articleLabelTypeIds") String articleLabelTypeIds) {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.deleteArticleLabelTypeByIds(articleLabelTypeIds));
    }
}