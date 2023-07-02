package com.blog.common.enums.mq;

/**
 * @description: mq topic基础类
 * @Author: 308501
 * @date 2023/6/28 16:50
 */

public enum TopicEnum {

    // 博客数据类数据统计
    BLOG_DATE_STATISTICS("BLOG", "BLOG_STATISTICS", "COUNT"),

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

    TopicEnum(String group, String topic, String tag) {
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
