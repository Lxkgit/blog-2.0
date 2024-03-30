package com.blog.pi.netty.listener.service.vo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @description: 舵机命令下发参数
 * @Author: 308501
 * @date 2024/2/2 11:06
 */


public class SteeringEngineVo extends SensorCommandVo {

    /**
     * 舵机旋转角度
     */
    @JSONField(name = "data")
    private Integer data;

    /**
     * 命令延时 多条组合命令延时使用,分隔
     */
    @JSONField(name = "controlIntervalTime")
    private Integer controlIntervalTime;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }

    public Integer getControlIntervalTime() {
        return controlIntervalTime;
    }

    public void setControlIntervalTime(Integer controlIntervalTime) {
        this.controlIntervalTime = controlIntervalTime;
    }
}
