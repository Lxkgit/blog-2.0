package com.blog.pi.netty.listener.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blog.pi.enums.mqtt.MQTTTopicEnum;
import com.blog.pi.mqtt.MqttPushClient;
import com.blog.pi.netty.listener.service.enums.SensorTypeEnum;
import com.blog.pi.netty.listener.service.vo.SensorCommandVo;
import com.blog.pi.netty.listener.service.vo.SteeringEngineVo;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**
 * @description: 传感器控制服务类
 * @Author: 308501
 * @date 2024/3/29 19:56
 */

@Component
public class SensorControlService {

    public void sendCommand(String command) {
        JSONObject jsonObject = JSONObject.parseObject(command);

//        Class<? extends SensorCommandVo> sensorCommandVo = SensorTypeEnum.getRuleImpl(jsonObject.get("sensorType").toString());

        if (SensorTypeEnum.DUO_JI.getSensorCode().equals(jsonObject.get("sensorType"))) {
            SteeringEngineVo steeringEngineVo = new SteeringEngineVo();
            steeringEngineVo.setSensorType(jsonObject.getString("sensorType"));
            steeringEngineVo.setChipType(jsonObject.getString("chipType"));
            JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject.get("commandList").toString());
            for (int i=0; i<jsonArray.size(); i++) {
                steeringEngineVo.setData(jsonArray.get(i));
            }
        }

        System.out.println(jsonObject.get("commandList"));

        System.out.println(jsonArray.size());

        SensorCommandVo sensorCommandVo = new SensorCommandVo();


//        SensorCommandVo commandVo =
//                SensorTypeEnum.getRuleImpl("DUO");

//        try {
//            MqttPushClient.publish(MQTTTopicEnum.SENSOR_CONTROL.getTopic(), data);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
