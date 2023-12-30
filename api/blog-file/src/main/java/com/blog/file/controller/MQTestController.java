package com.blog.file.controller;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.result.Result;
import com.blog.common.result.ResultFactory;
import com.blog.file.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: mq
 * @Author: lxk
 * @date 2023/6/27 19:34
 */

@Slf4j
@RestController
@RequestMapping("/mq")
public class MQTestController {


    @Resource
    private MQProducerService mqProducerService;

    @GetMapping("/send")
    public Result selectArticleById(@RequestParam(value = "msg") Integer msg, @RequestParam(value = "type") Integer type, @RequestParam(value = "userId") Integer userId) {
        ContentCountVo contentCountVo = new ContentCountVo();
        contentCountVo.setUserId(userId);
        contentCountVo.setArticleCount(msg);
        contentCountVo.setDocCount(msg);
        contentCountVo.setDiaryCount(msg);
        RocketMQMessage  rocketMQMessage = new RocketMQMessage();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_USER_DATA.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_USER_DATA.getTag());
        rocketMQMessage.setMessage(JSON.toJSONString(contentCountVo));
        rocketMQMessage.setMqMsgType(1);
        mqProducerService.sendSyncOrderly(rocketMQMessage);
        return ResultFactory.buildSuccessResult();
    }

}
