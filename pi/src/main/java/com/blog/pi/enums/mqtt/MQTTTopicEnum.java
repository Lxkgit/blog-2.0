package com.blog.pi.enums.mqtt;

public enum MQTTTopicEnum {

    // 温湿度传感器消息
    MQTT_TEMPERATURE_HUMIDITY("MQTT_HEART_SMP", 2),
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
