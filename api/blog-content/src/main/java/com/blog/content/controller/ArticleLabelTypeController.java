package com.blog.content.controller;


import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import com.blog.common.exception.ValidException;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.UpdateGroup;
import com.blog.content.service.ArticleLabelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: lxk
 * @date 2022/6/17 14:24
 * @description: 文章标签分类接口
 */

@Slf4j
@RestController
@RequestMapping("/label/type")
public class ArticleLabelTypeController extends BaseController {

    @Autowired
    private ArticleLabelTypeService articleLabelTypeService;

    /**
     * 创建文章标签分类
     *
     * @param request
     * @param articleLabelTypeVo
     * @return
     */
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:insert')")
    public Result saveArticleLabelType(HttpServletRequest request, @RequestBody @Validated(value = {AddGroup.class}) ArticleLabelTypeVo articleLabelTypeVo) {
        articleLabelTypeVo.setUserId(getBlogUser(request).getId());
        return ResultFactory.buildSuccessResult(articleLabelTypeService.saveArticleLabelType(articleLabelTypeVo));
    }

    /**
     * 删除文章标签分类
     *
     * @param articleLabelTypeVo
     * @return
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:delete')")
    public Result deleteArticleLabelTypeByIds(@Validated(value = {DeleteGroup.class}) ArticleLabelTypeVo articleLabelTypeVo) throws ValidException {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.deleteArticleLabelTypeByIds(articleLabelTypeVo.getArticleLabelTypeIds()));
    }

    /**
     * 修改文章标签分类
     *
     * @param articleLabelTypeVo
     * @return
     */
    @PostMapping("/update")
    @PreAuthorize("hasAnyAuthority('sys:article:label:type:update')")
    public Result updateArticleLabelType(@RequestBody @Validated(value = {UpdateGroup.class}) ArticleLabelTypeVo articleLabelTypeVo) throws ValidException {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.updateArticleLabelType(articleLabelTypeVo));
    }

    /**
     * 查询文章标签分类接口
     *
     * @return
     */
    @GetMapping("/list")
    public Result getArticleLabelTypeList() {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.getArticleLabelTypeList());
    }
}