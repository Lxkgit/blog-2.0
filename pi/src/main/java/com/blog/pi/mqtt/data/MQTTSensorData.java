package com.blog.pi.mqtt.data;

import com.blog.pi.entity.SensorData;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @description: mqtt传感器数据
 * @Author: 308501
 * @date 2024/1/25 10:29
 */

@Getter
@Setter
public class MQTTSensorData extends SensorData {

    private String chipType;

    private String sensorType;

    private Map<String, String> dataMap;

}
