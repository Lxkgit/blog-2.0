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

/**
 * @description:
 * @Author: 308501
 * @date 2023/7/25 17:21
 */

@Slf4j
@RestController
@RequestMapping("/setting")
public class BlogSettingController {

    @Resource
    private BlogSettingService blogSettingService;

    @GetMapping("/id")
    public Result selectSettingById(@RequestParam(value = "id") Integer id) {
        return ResultFactory.buildSuccessResult(blogSettingService.selectBlogSettingById(id));
    }

    @GetMapping("/select")
    public Result selectSettingList(@RequestHeader HttpHeaders headers, @RequestParam(value = "settingType") String settingType) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        return ResultFactory.buildSuccessResult(blogSettingService.selectBlogSetting(blogUser, settingType));
    }

    @PostMapping("/update")
    public Result updateSetting(@RequestHeader HttpHeaders headers, @RequestBody BlogSettingVo blogSettingVo) {
        String token = String.valueOf(headers.get("Authorization"));
        BlogUser blogUser = JwtUtil.getUserInfo(token);
        blogSettingService.updateBlogSetting(blogUser, blogSettingVo);
        return ResultFactory.buildSuccessResult();
    }
}
