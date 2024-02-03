package com.blog.file.service;

import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.exception.ValidException;

/**
 * @description: 传感器控制服务类
 * @Author: 308501
 * @date 2024/2/2 10:34
 */

public interface SensorControlService {

    Integer createSensorControl(Integer userId, SensorControlVo sensorControlVo) throws ValidException;

    /**
     * 下发传感器控制命令
     */
    void controlSensor(Integer userId, Integer id) throws ValidException;
}
