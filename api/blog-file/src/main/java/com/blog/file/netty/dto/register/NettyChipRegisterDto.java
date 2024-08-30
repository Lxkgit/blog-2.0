package com.blog.file.netty.dto.register;

import lombok.Data;

import java.util.List;

/**
 * @Description netty 单片机设备注册
 * @Author lxk
 * @CreateTime 2024-08-30
 */

@Data
public class NettyChipRegisterDto {

    /**
     * 单片机名称
     */
    private String chipName;

    /**
     * 单片机编码
     */
    private String chipCode;

    /**
     * 单片机主板类型
     */
    private String chipType;

    /**
     * 备注信息
     */
    private String memo;

    /**
     * 单片机下传感器注册信息
     */
    List<NettySensorRegisterDto> sensorList;


}
