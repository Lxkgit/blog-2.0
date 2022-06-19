package com.blog.content.controller;

import com.blog.common.constant.Constant;
import com.blog.common.entity.content.ArticleLabelType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.content.service.ArticleLabelTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result getArticleLabelTypeList(@RequestParam(required = false, value = "type") String type, @RequestParam(required = false, value = "id", defaultValue = "0") Integer id){
        return ResultFactory.buildSuccessResult(articleLabelTypeService.getArticleLabelTypeList(type, id));
    }

    @PostMapping("/save")
    public Result saveArticleLabelType(@RequestBody ArticleLabelType articleLabelType){
        // 获取用户请求头，从请求头中获取token
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("Authorization");
        }
        try {
            BlogUser blogUser = JwtUtil.getUserInfo(token);
            articleLabelType.setUserId(blogUser.getId());
            int flag = articleLabelTypeService.saveArticleLabelType(articleLabelType);
            if (flag == 1){
                return ResultFactory.buildSuccessResult();
            } else {
                return ResultFactory.buildFailResult("标签分类保存失败 ... ");
            }

        } catch (Exception e){
            log.warn(Constant.JWTError, e);
        }
        return ResultFactory.buildFailResult("...");
    }

    @PostMapping("/update")
    public Result updateArticleLabelType(@RequestBody ArticleLabelType articleLabelType){
        int flag = articleLabelTypeService.updateArticleLabelType(articleLabelType);
        if (flag == 1) {
            return ResultFactory.buildSuccessResult("标签分类修改成功 ... ");
        }
        return ResultFactory.buildFailResult("标签分类修改失败 ... ");
    }

    @DeleteMapping("/delete")
    public Result deleteArticleLabelTypeByIds(@RequestParam(value = "articleLabelTypeIds") String articleLabelTypeIds) {
        return ResultFactory.buildSuccessResult(articleLabelTypeService.deleteArticleLabelTypeByIds(articleLabelTypeIds));
    }
}