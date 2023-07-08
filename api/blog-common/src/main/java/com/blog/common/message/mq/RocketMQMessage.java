package com.blog.common.message.mq;

import lombok.Data;

/**
 * @description:
 * @Author: 308501
 * @date 2023/7/5 14:12
 */

@Data
public class RocketMQMessage<T> {

    /**
     * mq topic
     */
    private String topic;

    /**
     * mq tag
     */
    private String tag;

    /**
     * mq消息类型 0：全量 1：增量
     */
    private Integer mqMsgType;

    /**
     * 消息体
     */
    private T message;

    public RocketMQMessage() {
    }

    public RocketMQMessage(String topic, String tag, Integer mqMsgType, T message) {
        this.topic = topic;
        this.tag = tag;
        this.mqMsgType = mqMsgType;
        this.message = message;
    }
}
