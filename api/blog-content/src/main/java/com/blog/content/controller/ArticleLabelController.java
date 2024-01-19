package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.vo.ArticleLabelVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.content.service.ArticleLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

/**
 * @author: lxk
 * @date: 2022/6/15 18:54
 * @description: 文章标签接口
 * @modified By:
 */

@Slf4j
@RestController
@RequestMapping("/article/label")
public class ArticleLabelController extends BaseController {


    @Autowired
    private ArticleLabelService articleLabelService;

    /**
     * 创建文章标签
     *
     * @param request
     * @param articleLabelVo
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:article:label:insert')")
    public Result saveArticleLabel(HttpServletRequest request, @RequestBody @Validated(value = {AddGroup.class}) ArticleLabelVo articleLabelVo) throws ValidException {
        BlogUser blogUser = getBlogUser(request);
        articleLabelVo.setUserId(blogUser.getId());
        return ResultFactory.buildSuccessResult(articleLabelService.saveArticleLabel(articleLabelVo));
    }

    /**
     * 删除文章标签
     *
     * @param request
     * @param articleLabelVo
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:article:label:delete')")
    public Result deleteArticleLabelByIds(HttpServletRequest request, @Validated(value = {DeleteGroup.class}) ArticleLabelVo articleLabelVo) throws ValidException {
        return ResultFactory.buildSuccessResult(articleLabelService.deleteArticleLabelByIds(articleLabelVo.getIds(), getBlogUser(request).getId()));
    }

    /**
     * 修改文章标签
     *
     * @param articleLabelVo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:article:label:update')")
    public Result updateArticleLabel(HttpServletRequest request, @RequestBody @Validated(value = {UpdateGroup.class}) ArticleLabelVo articleLabelVo) throws ValidException {
        articleLabelVo.setUserId(getBlogUser(request).getId());
        return ResultFactory.buildSuccessResult(articleLabelService.updateArticleLabel(articleLabelVo));
    }

    /**
     * 查询文章标签列表
     *
     * @param articleLabelVo
     * @return
     */
    @GetMapping("/list")
    public Result selectArticleLabelList(@Validated(value = {SelectIdGroup.class}) ArticleLabelVo articleLabelVo) {
        return ResultFactory.buildSuccessResult(articleLabelService.selectArticleLabelList(articleLabelVo.getLabelType()));
    }
}
