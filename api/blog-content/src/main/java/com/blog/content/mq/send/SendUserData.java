package com.blog.content.mq.send;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.content.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: MQ消息快速发送类 BLOG_USER_DATA("BLOG_USER_DATA", "CONTENT" , "博客用户内容统计数据"),
 * @Author: 308501
 * @date 2023/12/27 14:31
 */

@Slf4j
@Component
public class SendUserData {

    // 发送文章
    public static final Integer article = 1;
    // 发送日记
    public static final Integer diary = 2;
    // 发送文档
    public static final Integer doc = 3;

    @Resource
    private MQProducerService mqProducerService;

    /**
     * 消息中组装单一种类数据
     * @param type  1: 发送文章 2: 发送日记 3: 发送文档
     * @param userId 用户id
     * @param count 数量
     */
    public void sendUserData(Integer type, Integer userId, Integer count) {
        ContentCountVo contentCountVo = new ContentCountVo();
        contentCountVo.setUserId(userId);
        if (type.equals(article)) {
            contentCountVo.setArticleCount(count);
        } else if (type.equals(diary)) {
            contentCountVo.setDiaryCount(count);
        } else if (type.equals(doc)) {
            contentCountVo.setDocCount(count);
        } else {
            return;
        }
        RocketMQMessage rocketMQMessage = new RocketMQMessage(RocketMQTopicEnum.BLOG_USER_DATA.getTopic(),
                RocketMQTopicEnum.BLOG_USER_DATA.getTag(), 1, JSON.toJSONString(contentCountVo));
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }
}
