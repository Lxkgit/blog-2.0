package com.blog.file.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.common.entity.file.ContentCount;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.TopicEnum;
import com.blog.file.dao.ContentCountDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: mq消息消费服务
 * @Author: 308501
 * @date 2023/6/26 20:00
 */

@Slf4j
@Component
public class MQConsumerService {



    // topic需要和生产者的topic一致，consumerGroup属性是必须指定的，内容可以随意
    // selectorExpression的意思指的就是tag，默认为“*”，不设置的话会监听所有消息
    // 接收mq消息： TopicEnum.BLOG_DATE_STATISTICS

    /**
     * 监听用户个人数据
     */
    @Service
    @RocketMQMessageListener(consumerGroup = "BLOG_STATISTICS_COUNT", topic = "BLOG_STATISTICS", selectorExpression = "COUNT")
    public static class ConsumerSend implements RocketMQListener<ContentCountVo> {

        @Resource
        private ContentCountDAO contentCountDAO;

        private static Boolean flag;

        // 监听到消息就会执行此方法
        @Override
        public void onMessage(ContentCountVo o) {
            log.info("监听到消息 topic:{}, tag:{}, msg:{} ",TopicEnum.BLOG_DATE_STATISTICS.getTopic(),
                    TopicEnum.BLOG_DATE_STATISTICS.getTag(), JSON.toJSONString(o));
            if (o.getMqMsgType() == null) {
                log.info("mqMsgType 字段为空");
                return;
            }
            synchronized(this) {
                log.info("当前线程: " + Thread.currentThread().getName());
                if (o.getMqMsgType() == 0) {
                    QueryWrapper<ContentCount> contentCountQueryWrapper = new QueryWrapper<>();
                    contentCountQueryWrapper.eq("user_id", o.getUserId());
                    List<ContentCount> userList = contentCountDAO.selectList(contentCountQueryWrapper);
                    log.info("userList: " + userList);
                    if (userList != null && userList.size()>0) {
                        log.info("删除：" + userList);
                        contentCountDAO.delete(contentCountQueryWrapper);
                    }
                    log.info("新增：userId" + o.getUserId());
                    contentCountDAO.insert(o);
                } else if (o.getMqMsgType() == 1) {
                    QueryWrapper<ContentCount> wrapper = new QueryWrapper<>();
                    wrapper.eq("user_id", o.getUserId());
                    List<ContentCount> list = contentCountDAO.selectList(wrapper);
                    if (list != null && list.size() >0) {
                        ContentCount count = list.get(0);
                        if (o.getArticleCount() != null) {
                            o.setArticleCount(count.getArticleCount() + o.getArticleCount());
                        }
                        if (o.getDiaryCount() != null) {
                            o.setDiaryCount(count.getDiaryCount() + o.getDiaryCount());
                        }
                        if (o.getDocCount() != null) {
                            o.setDocCount(count.getDocCount() + o.getDocCount());
                        }
                        contentCountDAO.update(o, wrapper);
                    }
                }
            }
        }
    }
}
