package com.blog.common.message.socket;

import lombok.Data;

/**
 * @description:
 * @Author: 308501
 * @date 2023/7/5 14:24
 */

@Data
public class SocketMessage<T> {

    /**
     * socket topic
     */
    private String topic;

    /**
     * 发送消息用户id
     */
    private Integer userId;

    /**
     * socket 消息体
     */
    private T message;

}
