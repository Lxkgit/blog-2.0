package com.blog.common.enums.mq;

/**
 * @description: mq topic基础类
 * @Author: 308501
 * @date 2023/6/28 16:50
 */

public enum RocketMQTopicEnum {

    // 博客数据类数据统计
    MQ_DATE_STATISTICS("BLOG_STATISTICS_COUNT", "BLOG_STATISTICS", "COUNT"),
    BLOG_STATISTICS_OVERALL("BLOG_STATISTICS_OVERALL", "BLOG_STATISTICS", "OVERALL"),

    ;

    /**
     * group
     */
    private String group;

    /**
     * topic
     */
    private String topic;

    /**
     * tag
     */
    private String tag;

    RocketMQTopicEnum(String group, String topic, String tag) {
        this.group = group;
        this.topic = topic;
        this.tag = tag;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
}
