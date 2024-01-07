package com.blog.pi.enums.mqtt;

public enum MQTTTopicEnum {

    // 树莓派心跳
    MQTT_HEART_SMP("MQTT_HEART_SMP", 0),

    /**
     * 文件同步
     */
    // 文件消息发送队列  同步文件时将需要同步的文件放入此队列
    MQTT_SEND_SYNC_FILE("MQTT_SYNC_FILE", 1),
    // 文件消息接收队列 负责接收请求同步的文件响应消息
    MQTT_RECEIVE_SYNC_FILE("MQTT_RECEIVE_SYNC_FILE", 2),

    ;


    /**
     * topic
     */
    private String topic;

    /**
     * mqtt qos 等级
     */
    private Integer qos;

    MQTTTopicEnum(String topic, Integer qos) {
        this.topic = topic;
        this.qos = qos;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getQos() {
        return qos;
    }

    public void setQos(Integer qos) {
        this.qos = qos;
    }
}
