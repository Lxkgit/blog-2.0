package com.blog.file.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.List;

/**
 * RocketMQConsumeMsgListenerProcessor
 */
@Component
@Slf4j
public class RocketMQConsumeMsgListenerProcessor implements MessageListenerConcurrently {

    @Resource
    private RockerMQMsgConsumer rockerMQMsgConsumer;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgList)) {
            log.info("接受到的消息为空，不处理，直接返回成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        MessageExt messageExt = msgList.get(0);
        int reconsume = messageExt.getReconsumeTimes();
        if (reconsume == 3) {//消息已经重试了3次，如果不需要再次消费，则返回成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        String msgBody = new String(messageExt.getBody(), Charset.forName(RemotingHelper.DEFAULT_CHARSET));
        RocketMQMessage rocketMQMessage = JSON.parseObject(msgBody, RocketMQMessage.class);
        String topic = rocketMQMessage.getTopic();
        String tag = rocketMQMessage.getTag();
        log.info("RocketMQ消息处理: " + rocketMQMessage.toString());
        if (topic.equals(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic())) { // 系统消息处理
            if (tag.equals(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag())) { // 博客内容数据全局统计
                rockerMQMsgConsumer.dealBlogSystemData(rocketMQMessage);
            }
        } else if (topic.equals(RocketMQTopicEnum.BLOG_USER_DATA.getTopic())) { // 用户消息处理
            if (tag.equals(RocketMQTopicEnum.BLOG_USER_DATA.getTag())) {
                rockerMQMsgConsumer.dealBlogUserData(rocketMQMessage);
            }
        } else {

        }


        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

}