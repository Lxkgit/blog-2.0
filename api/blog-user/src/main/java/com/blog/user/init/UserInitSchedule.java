package com.blog.user.init;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.file.BlogData;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.user.dao.SysUserDAO;
import com.blog.user.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @Author: lxk
 * @date 2023/7/11 20:18
 */

@Slf4j
@Component
public class UserInitSchedule implements ApplicationRunner {

    @Resource
    private SysUserDAO sysUserDAO;

    @Resource
    private MQProducerService mqProducerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化博客用户数据 ... ");
        BlogData blogData = new BlogData();
        blogData.setUserCount(Math.toIntExact(sysUserDAO.selectCount(null)));
        RocketMQMessage rocketMQMessage = new RocketMQMessage();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag());
        rocketMQMessage.setMessage(JSON.toJSONString(blogData));
        rocketMQMessage.setMqMsgType(0);
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }
}
