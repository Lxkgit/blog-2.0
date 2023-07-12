package com.blog.file.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.UploadFile;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.file.FileTypeEnum;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.file.dao.UploadFileDAO;
import com.blog.file.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @Author: 308501
 * @date 2023/7/11 20:06
 */

@Slf4j
@Component
public class FileInitSchedule implements ApplicationRunner {

    @Resource
    private UploadFileDAO uploadFileDAO;

    @Resource
    private MQProducerService mqProducerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化博客文件数据 ... ");
        QueryWrapper<UploadFile> wrapper = new QueryWrapper<>();
        wrapper.in("file_type", FileTypeEnum.IMAGE.getTypeList());
        BlogData blogData = new BlogData();
        blogData.setImgCount(Math.toIntExact(uploadFileDAO.selectCount(wrapper)));
        RocketMQMessage<BlogData> rocketMQMessage = new RocketMQMessage<>();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTag());
        rocketMQMessage.setMessage(blogData);
        rocketMQMessage.setMqMsgType(0);
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }
}
