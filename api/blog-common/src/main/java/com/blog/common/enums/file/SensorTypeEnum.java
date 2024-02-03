package com.blog.common.enums.file;

import com.blog.common.entity.file.vo.sensor.control.SensorCommandVo;
import com.blog.common.entity.file.vo.sensor.control.SteeringEngineVo;

/**
 * 传感器类型枚举类
 */

public enum SensorTypeEnum {

    DUO_JI("duo", "舵机", SteeringEngineVo.class),
    ;


    /**
     * 传感器类型编码
     */
    private String sensorCode;

    private String sensorName;

    private Class<? extends SensorCommandVo> commandClass;

    SensorTypeEnum(String sensorCode, String sensorName, Class<? extends SensorCommandVo> analysisClass) {
        this.sensorCode = sensorCode;
        this.sensorName = sensorName;
        this.commandClass = analysisClass;
    }

    /**
     * 根据sensorCode返回对应的封装类
     *
     * @param sensorCode
     * @return
     */
    public static Class<? extends SensorCommandVo> getRuleImpl(String sensorCode) {
        for (SensorTypeEnum sensorTypeEnum : SensorTypeEnum.values()) {
            if (sensorTypeEnum.getSensorCode().equals(sensorCode)) {
                if (sensorTypeEnum.getCommandClass() != null) {
                    return sensorTypeEnum.getCommandClass();
                }
                break;
            }
        }
        return null;
    }

    public String getSensorCode() {
        return sensorCode;
    }

    public void setSensorCode(String sensorCode) {
        this.sensorCode = sensorCode;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Class<? extends SensorCommandVo> getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(Class<? extends SensorCommandVo> commandClass) {
        this.commandClass = commandClass;
    }
}
