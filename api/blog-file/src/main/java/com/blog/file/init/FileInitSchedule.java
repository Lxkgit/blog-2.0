package com.blog.file.init;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.UploadFile;
import com.blog.common.enums.file.FileTypeEnum;
import com.blog.common.enums.mq.RocketMQMsgEnum;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.file.dao.FileDataDAO;
import com.blog.file.dao.UploadFileDAO;
import com.blog.file.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @description:
 * @Author: lxk
 * @date 2023/7/11 20:06
 */

@Slf4j
@Configuration     //证明这个类是一个配置文件
@EnableScheduling  //启用定时器
public class FileInitSchedule {

    @Resource
    private FileDataDAO fileDataDAO;

    @Resource
    private MQProducerService mqProducerService;


    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void initFile() {
        log.info("开始初始化博客文件数据 ... ");
        BlogData blogData = new BlogData();
        blogData.setImgCount(fileDataDAO.selectImgCount());
        RocketMQMessage rocketMQMessage = new RocketMQMessage();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag());
        rocketMQMessage.setMessage(JSON.toJSONString(blogData));
        rocketMQMessage.setMqMsgType(RocketMQMsgEnum.ALL.getType());
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }
}
