package com.blog.pi.netty.listener.service.vo;

import lombok.Data;

/**
 * @description: 传感器命令基础类
 * @Author: 308501
 * @date 2024/3/29 20:00
 */

@Data
public class SensorCommandVo {

    private String chipType;

    private String sensorType;
}
