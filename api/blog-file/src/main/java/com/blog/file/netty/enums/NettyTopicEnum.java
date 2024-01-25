package com.blog.file.netty.enums;

/**
 * @description: Netty 消息枚举类
 * @Author: 308501
 * @date 2024/1/11 10:01
 */

public enum NettyTopicEnum {

    // 博客MySQL数据与数据文件同步
    BLOG_FILE_SYNC("BLOG_FILE_SYNC"),
    // 传感器数据消息
    BLOG_SENSOR_DATA("BLOG_SENSOR_DATA"),


    ;
    /**
     * topic
     */
    private String topic;

    NettyTopicEnum(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
