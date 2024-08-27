package com.blog.file.netty.enums.sensor;


import com.blog.file.netty.dto.sensor.control.SensorCommandCheckDto;
import com.blog.file.netty.dto.sensor.control.SteeringEngineDto;

/**
 * 传感器类型枚举类
 */

public enum SensorTypeEnum {

    DUO_JI("DUO", "舵机", SteeringEngineDto.class),
    ;


    /**
     * 传感器类型编码
     */
    private String sensorCode;

    private String sensorName;

    private Class<? extends SensorCommandCheckDto> commandClass;

    SensorTypeEnum(String sensorCode, String sensorName, Class<? extends SensorCommandCheckDto> analysisClass) {
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
    public static Class<? extends SensorCommandCheckDto> getRuleImpl(String sensorCode) {
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

    public Class<? extends SensorCommandCheckDto> getCommandClass() {
        return commandClass;
    }

    public void setCommandClass(Class<? extends SensorCommandCheckDto> commandClass) {
        this.commandClass = commandClass;
    }
}
