package com.blog.common.message.mqtt;

import lombok.Data;

/**
 * @description: mqtt发送消息类
 * @Author: 308501
 * @date 2024/1/5 15:55
 */

@Data
public class MqttMessage {

    private String sender;

    private String receiver;

    private String message;



}
