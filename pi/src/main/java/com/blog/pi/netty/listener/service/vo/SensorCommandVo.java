package com.blog.pi.netty.listener.service.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @description: 传感器命令基础类
 * @Author: lxk
 * @date 2024/3/29 20:00
 */

@Data
public class SensorCommandVo {

    /**
     *
     */
    private String chipType;

    /**
     *
     */
    private String sensorType;

    /**
     * 命令延时 多条组合命令延时使用,分隔
     */
    private Integer controlIntervalTime;
}
