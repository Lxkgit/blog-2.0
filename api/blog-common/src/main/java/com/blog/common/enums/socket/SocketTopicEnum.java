package com.blog.common.enums.socket;

/**
 * @description: socket topic
 * @Author: 308501
 * @date 2023/7/5 17:13
 */

public enum SocketTopicEnum {

    // 用户内容推送
    SOCKET_USER_CONTENT_DATA("USER_CONTENT_DATA"),
    // 聊天topic
    SOCKET_CHAT("CHAT"),
    // 系统消息
    SOCKET_SYSTEM("SYSTEM"),

    ;

    /**
     * topic
     */
    private String topic;

    SocketTopicEnum(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
