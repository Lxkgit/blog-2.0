package com.blog.common.entity.file.vo.sensor.control;

import com.alibaba.fastjson.annotation.JSONField;
import com.blog.common.valication.group.AddGroup;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @description: 舵机命令下发参数
 * @Author: 308501
 * @date 2024/2/2 11:06
 */


public class SteeringEngineVo extends SensorCommandVo {

    @Max(value = 180,message="舵机参数范围为0-180",groups={AddGroup.class})
    @Min(value = 0,message="舵机参数范围为0-180",groups={AddGroup.class})
    @JSONField(name = "data")
    private Integer data;

    public Integer getData() {
        return data;
    }

    public void setData(Integer data) {
        this.data = data;
    }
}
