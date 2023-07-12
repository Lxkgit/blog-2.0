package com.blog.file.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.ContentCount;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.socket.SocketTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.message.socket.SocketMessage;
import com.blog.file.dao.BlogDataDAO;
import com.blog.file.dao.ContentCountDAO;
import com.blog.file.socket.SendMessage;
import com.blog.file.socket.SendObjectMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
    @RocketMQMessageListener(consumerGroup = "BLOG_STATISTICS_COUNT", topic = "BLOG_STATISTICS", selectorExpression = "COUNT", consumeMode = ConsumeMode.ORDERLY)
    public static class ConsumerSend implements RocketMQListener<RocketMQMessage<ContentCountVo>> {

        @Resource
        private ContentCountDAO contentCountDAO;

        // 监听到消息就会执行此方法
        @Override
        public void onMessage(RocketMQMessage<ContentCountVo> rocketMQMessage) {

            log.info("监听到消息 topic:{}, tag:{}, msg:{} ", rocketMQMessage.getTopic(), rocketMQMessage.getTag(), rocketMQMessage.getMessage().toString());
            ContentCountVo contentCountVo = rocketMQMessage.getMessage();
            if (rocketMQMessage.getMqMsgType() == null) {
                return;
            }
            if (rocketMQMessage.getMqMsgType() == 0) {
                QueryWrapper<ContentCount> contentCountQueryWrapper = new QueryWrapper<>();
                contentCountQueryWrapper.eq("user_id", contentCountVo.getUserId());
                List<ContentCount> userList = contentCountDAO.selectList(contentCountQueryWrapper);
                if (userList != null && userList.size() > 0) {
                    contentCountDAO.delete(contentCountQueryWrapper);
                }
                contentCountDAO.insert(contentCountVo);
            } else if (rocketMQMessage.getMqMsgType() == 1) {
                QueryWrapper<ContentCount> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id", contentCountVo.getUserId());
                List<ContentCount> list = contentCountDAO.selectList(wrapper);
                if (list != null && list.size() > 0) {
                    ContentCount count = list.get(0);
                    if (contentCountVo.getArticleCount() != null) {
                        contentCountVo.setArticleCount(count.getArticleCount() + contentCountVo.getArticleCount());
                    }
                    if (contentCountVo.getDiaryCount() != null) {
                        contentCountVo.setDiaryCount(count.getDiaryCount() + contentCountVo.getDiaryCount());
                    }
                    if (contentCountVo.getDocCount() != null) {
                        contentCountVo.setDocCount(count.getDocCount() + contentCountVo.getDocCount());
                    }
                    contentCountDAO.update(contentCountVo, wrapper);
                }
            }

            QueryWrapper<ContentCount> contentCountQueryWrapper = new QueryWrapper<>();
            contentCountQueryWrapper.eq("user_id", contentCountVo.getUserId());
            List<ContentCount> userList = contentCountDAO.selectList(contentCountQueryWrapper);
            if (userList != null && userList.size() > 0) {
                SocketMessage<ContentCountVo> socketMessage = new SocketMessage<>();
                socketMessage.setTopic(SocketTopicEnum.SOCKET_USER_CONTENT_DATA.getTopic());
                socketMessage.setUserId(contentCountVo.getUserId());
                socketMessage.setMessage(contentCountVo);
                SendMessage.sendMessageToUser(JSON.toJSONString(socketMessage), contentCountVo.getUserId());
            }
        }
    }

    /**
     * 监听博客全局统计数据
     * 博客网站点击数 博客总文章数 等数据
     */
    @Service
    @RocketMQMessageListener(consumerGroup = "BLOG_STATISTICS_OVERALL", topic = "BLOG_STATISTICS", selectorExpression = "OVERALL", consumeMode = ConsumeMode.ORDERLY)
    public static class ConsumerGlobalData implements RocketMQListener<RocketMQMessage<BlogDataVo>> {

        @Resource
        private BlogDataDAO blogDataDAO;

        // 监听到消息就会执行此方法
        @Override
        public void onMessage(RocketMQMessage<BlogDataVo> rocketMQMessage) {

            log.info("监听到消息 topic:{}, tag:{}, msg:{} ", rocketMQMessage.getTopic(), rocketMQMessage.getTag(), rocketMQMessage.getMessage().toString());
            if (rocketMQMessage.getMqMsgType() == null) {
                return;
            }
            if (rocketMQMessage.getMqMsgType() == 0) {
                BlogData blogData = blogDataDAO.selectById(1);
                BlogData rocketMsg = rocketMQMessage.getMessage();
                rocketMsg.setId(1);
                if (blogData == null) {
                    blogDataDAO.insert(rocketMsg);
                } else {
                    blogDataDAO.updateById(rocketMQMessage.getMessage());
                }
            } else {
                BlogData blogData = rocketMQMessage.getMessage();
                blogDataDAO.updateBlogDataById(blogData);
            }

            QueryWrapper<BlogData> wrapper = new QueryWrapper<>();
            wrapper.eq("id", 1);
            BlogData blogData = blogDataDAO.selectById(1);
            System.out.println(blogData.toString());
            SocketMessage<BlogData> socketMessage = new SocketMessage<>();
            socketMessage.setTopic(SocketTopicEnum.SOCKET_SYSTEM_DATA.getTopic());
            socketMessage.setMessage(blogData);
            SendObjectMessage.sendInfo(JSON.toJSONString(socketMessage));

        }
    }
}
