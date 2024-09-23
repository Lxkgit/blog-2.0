package com.blog.common.netty.dto.sensor.control;

import lombok.Data;

import java.util.List;

/**
 * @description: 传感器下发命令Vo类
 * @Author: lxk
 * @date 2024/2/2 11:04
 */


@Data
public class SensorCommandDto<T> {

    /**
     * 设备编码
     */
    private String chipCode;

    /**
     * 传感器编码
     */
    private String sensorCode;

    /**
     * 下发控制消息命令列表
     */
    private List<T> commandList;

}
