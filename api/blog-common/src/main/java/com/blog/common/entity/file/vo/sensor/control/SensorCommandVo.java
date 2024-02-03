package com.blog.common.entity.file.vo.sensor.control;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 传感器下发命令Vo类
 * @Author: 308501
 * @date 2024/2/2 11:04
 */


public class SensorCommandVo {

    private String chipType;

    private String sensorType;

    public String getChipType() {
        return chipType;
    }

    public void setChipType(String chipType) {
        this.chipType = chipType;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }
}
