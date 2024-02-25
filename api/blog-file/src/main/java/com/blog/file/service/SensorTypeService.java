package com.blog.file.service;

import com.blog.common.entity.file.SensorType;

import java.util.List;

/**
 * @description: 传感器类型服务类
 * @Author: 308501
 * @date 2024/2/22 14:54
 */

public interface SensorTypeService {

    List<SensorType> selectSensorTypeList();
}
