package com.blog.pi.netty.listener.service.thread;

import com.alibaba.fastjson.JSONObject;
import com.blog.pi.enums.mqtt.MQTTTopicEnum;
import com.blog.pi.mqtt.MqttPushClient;
import com.blog.pi.netty.listener.service.vo.SensorCommandVo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @description: 命令执行线程
 * @Author: lxk
 * @date 2024/3/30 17:24
 */

@Slf4j
public class CommandThread extends Thread {

    private final String sensorCommandType;

    public CommandThread(String sensorCommandType) {
        CommandThreadService.threadSet.add(sensorCommandType);
        this.sensorCommandType = sensorCommandType;
    }

    @Override
    public void run() {
        try {
            log.info(">>>> 传感器 {} 正在执行命令", sensorCommandType);
            while (CommandThreadService.commandMap.get(sensorCommandType) != null && CommandThreadService.commandMap.get(sensorCommandType).size() > 0) {
                SensorCommandVo sensorCommandVo = CommandThreadService.commandMap.get(sensorCommandType).get(0);
                Thread.sleep(sensorCommandVo.getControlIntervalTime() * 1000);
                String command = JSONObject.toJSONString(sensorCommandVo);
                log.info(">>>> 执行命令: {}", command);
                MqttPushClient.publish(MQTTTopicEnum.SENSOR_CONTROL.getTopic(), command);
                CommandThreadService.commandMap.get(sensorCommandType).remove(sensorCommandVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommandThreadService.threadSet.remove(sensorCommandType);
        }
    }
}
