package com.blog.file.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * RocketMQConfig
 */

@Configuration
@Slf4j
public class RocketMQConfig {

    @Value("${rocketmq.consumer.groupName}")
    protected String groupName;

    @Value("${rocketmq.consumer.namesrvAddr}")
    protected String nameSrvAddr;

    @Value("${rocketmq.consumer.topics}")
    protected String topic;

    @Value("${rocketmq.consumer.consumeThreadMin}")
    protected int min;

    @Value("${rocketmq.consumer.consumeThreadMax}")
    protected int max;

    @Value("${rocketmq.consumer.consumeMessageBatchMaxSize}")
    protected int messageMaxSize;

    @Resource
    private RocketMQConsumeMsgListenerProcessor rocketMQConsumeMsgListenerProcessor;

    /**
     * 消费者
     *
     * @return
     */
    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() {

        log.info("groupName " + groupName + " nameSrvAddr " + nameSrvAddr + " topic " + topic);

        if (StringUtils.isEmpty(groupName)) {
            throw new RuntimeException("rocketMq consumer groupName is null !");
        }
        if (StringUtils.isEmpty(nameSrvAddr)) {
            throw new RuntimeException("rocketMq consumer namesrvAddr is null !");
        }
        if (StringUtils.isEmpty(topic)) {
            throw new RuntimeException("rocketMq consumer topics is null !");
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(nameSrvAddr);
        consumer.setConsumeThreadMin(min);
        consumer.setConsumeThreadMax(max);

        // 消息消费处理类
        consumer.registerMessageListener(rocketMQConsumeMsgListenerProcessor);

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        // 设置消费模型，集群还是广播，默认为集群
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置一次消费消息的条数，默认为1条
        consumer.setConsumeMessageBatchMaxSize(messageMaxSize);
        try {
            // 设置该消费者订阅的主题和tag，如果是订阅该主题下的所有tag，则tag使用*; 如果需要指定订阅该主题下的某些tag，则使用||分割，例如tag1||tag2||tag3
            String[] topicTagsArr = topic.split(";");
            for (String topicTags : topicTagsArr) {
                String[] topicTag = topicTags.split("~");
                consumer.subscribe(topicTag[0], topicTag[1]);
            }
            consumer.start();
            log.info("consumer start success!!! groupName:{},topics:{},namesrvAddr:{}", groupName, topic, nameSrvAddr);
        } catch (MQClientException e) {
            log.info("consumer start failed!!! groupName:{},topics:{},namesrvAddr:{}", groupName, topic, nameSrvAddr, e);
            throw new RuntimeException(e);
        }
        return consumer;
    }
}