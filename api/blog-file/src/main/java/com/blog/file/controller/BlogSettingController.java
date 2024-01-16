package com.blog.file.controller;

import com.blog.common.entity.file.vo.BlogSettingVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.common.util.JwtUtil;
import com.blog.file.service.BlogSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @Author: lxk
 * @date 2023/7/25 17:21
 */

@Slf4j
@RestController
@RequestMapping("/setting")
public class BlogSettingController extends BaseController {

    @Resource
    private BlogSettingService blogSettingService;

    @GetMapping("/id")
    public Result selectSettingById(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(blogSettingService.selectBlogSettingById(id));
    }

    @GetMapping("/select")
    public Result selectSettingList(HttpServletRequest request, @RequestParam(value = "settingType") String settingType) {
        return ResultFactory.buildSuccessResult(blogSettingService.selectBlogSetting(getBlogUser(request), settingType));
    }

    @PostMapping("/update")
    public Result updateSetting(HttpServletRequest request, @RequestBody BlogSettingVo blogSettingVo) {
        blogSettingService.updateBlogSetting(getBlogUser(request), blogSettingVo);
        return ResultFactory.buildSuccessResult();
    }
}
