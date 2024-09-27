package com.blog.common.netty.dto.sensor.control;

import com.alibaba.fastjson.annotation.JSONField;
import com.blog.common.valication.group.AddGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @description: 舵机命令下发参数
 * @Author: lxk
 * @date 2024/2/2 11:06
 */


@Data
@EqualsAndHashCode(callSuper = true)
public class SteeringEngine360Dto extends SensorCommandCheckDto {

    /**
     * 舵机命令控制旋转角度
     */
    @Max(value = 360,message="舵机参数范围为0-360",groups={AddGroup.class})
    @Min(value = 0,message="舵机参数范围为0-360",groups={AddGroup.class})
    @JSONField(name = "data")
    private Integer data;

}
