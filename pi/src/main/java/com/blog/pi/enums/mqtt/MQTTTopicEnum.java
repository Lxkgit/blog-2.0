package com.blog.pi.enums.mqtt;

public enum MQTTTopicEnum {

    // 芯片心跳
    CHIP_HEART("CHIP_HEART", 0),

    // 传感器数据
    SENSOR_DATA("SENSOR_DATA", 0),

    // 传感器控制
    SENSOR_CONTROL("SENSOR_CONTROL", 2),
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
