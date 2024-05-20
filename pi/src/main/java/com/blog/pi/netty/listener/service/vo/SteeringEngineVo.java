package com.blog.pi.netty.listener.service.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @description: 舵机命令下发参数
 * @Author: lxk
 * @date 2024/2/2 11:06
 */

public class SteeringEngineVo extends SensorCommandVo {

    /**
     * 舵机旋转角度
     */
    @JSONField(name = "data")
    private Integer data;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
