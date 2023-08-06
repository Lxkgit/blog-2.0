package com.blog.file.controller;

import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.file.service.BlogDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 博客数据详情接口
 * @Author: lxk
 * @date 2023/7/14 11:34
 */

@Slf4j
@RestController
@RequestMapping("/data")
public class BlogDataController {


    @Resource
    private BlogDataService blogDataService;

    @GetMapping
    public Result selectBlogData() {
        return ResultFactory.buildSuccessResult(blogDataService.selectBlogData());
    }
}
