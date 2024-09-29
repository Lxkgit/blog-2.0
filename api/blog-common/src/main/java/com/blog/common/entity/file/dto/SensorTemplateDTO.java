package com.blog.common.entity.file.dto;

import com.blog.common.entity.file.SensorTemplate;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 传感器模板页面数据接收类
 * @Author lxk
 * @CreateTime 2024-09-29
 */

@Getter
@Setter
public class SensorTemplateDTO extends SensorTemplate {

    /**
     * 单片机id
     */
    private Integer chipId;

    /**
     * 传感器id
     */
    private Integer sensorId;
}
