package com.blog.common.netty.enums.sensor;


import com.blog.common.netty.dto.sensor.control.SensorCommandCheckDto;
import com.blog.common.netty.dto.sensor.control.SteeringEngineDto;
import lombok.Getter;

/**
 * 传感器类型枚举类
 */

@Getter
public enum SensorTypeEnum {

    DUO_JI("DUO", "舵机", SteeringEngineDto.class),
    ;


    /**
     * 传感器类型编码
     */
    private String sensorType;

    private String sensorName;

    private Class<? extends SensorCommandCheckDto> commandClass;

    SensorTypeEnum(String sensorType, String sensorName, Class<? extends SensorCommandCheckDto> analysisClass) {
        this.sensorType = sensorType;
        this.sensorName = sensorName;
        this.commandClass = analysisClass;
    }

    /**
     * 根据sensorCode返回对应的封装类
     *
     * @param sensorType
     * @return
     */
    public static Class<? extends SensorCommandCheckDto> getRuleImpl(String sensorType) {
        for (SensorTypeEnum sensorTypeEnum : SensorTypeEnum.values()) {
            if (sensorTypeEnum.getSensorType().equals(sensorType)) {
                if (sensorTypeEnum.getCommandClass() != null) {
                    return sensorTypeEnum.getCommandClass();
                }
                break;
            }
        }
        return null;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
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
