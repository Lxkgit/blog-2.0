package com.blog.common.enums.mq;

/**
 * @description: mq topic基础类
 * @Author: lxk
 * @date 2023/6/28 16:50
 */

public enum RocketMQTopicEnum {

    // 博客数据类数据统计
    BLOG_USER_DATA("BLOG_USER_DATA", "CONTENT" , "博客用户内容统计数据"),
    BLOG_SYSTEM_DATA("BLOG_SYSTEM_DATA", "CONTENT", "博客系统内容消息"),

    ;

    /**
     * topic
     */
    private String topic;

    /**
     * tag
     */
    private String tag;

    /**
     * 备注
     */
    private String memo;

    RocketMQTopicEnum(String topic, String tag, String memo) {
        this.topic = topic;
        this.tag = tag;
        this.memo = memo;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
