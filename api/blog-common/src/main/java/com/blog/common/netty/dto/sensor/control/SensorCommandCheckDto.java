package com.blog.common.netty.dto.sensor.control;

import com.alibaba.fastjson.annotation.JSONField;
import com.blog.common.valication.group.AddGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @description: 传感器下发命令参数校验基础类
 * @Author: lxk
 * @date 2024/2/2 11:04
 */


@Getter
@Setter
public class SensorCommandCheckDto {


    @Max(value = 10000,message="命令等待延时范围是0-10000ms",groups={AddGroup.class})
    @Min(value = 0,message="命令等待延时范围是0-10000ms",groups={AddGroup.class})
    @JSONField(name = "delay")
    private Integer delay;

    /**
     * 传感器类型
     */
    private String sensorType;
}
