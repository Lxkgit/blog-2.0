package com.blog.file.netty.vo.sensor.control;

import lombok.Data;

import java.util.List;

/**
 * @description: 传感器下发命令Vo类
 * @Author: lxk
 * @date 2024/2/2 11:04
 */


@Data
public class SensorCommandVo<T> {

    /**
     * 设备类型
     */
    private String chipType;

    /**
     * 设备编码
     */
    private String chipCode;

    /**
     * 传感器类型
     */
    private String sensorType;

    /**
     * 传感器编码
     */
    private String sensorCode;

    /**
     * 下发消息编码
     */
    private String msgCode;

    /**
     * 下发控制消息命令列表
     */
    private List<T> commandList;

}
