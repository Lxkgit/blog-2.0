package com.blog.file.service;

import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;

/**
 * @description: 传感器控制服务类
 * @Author: 308501
 * @date 2024/2/2 10:34
 */

public interface SensorControlService {

    Integer createSensorControl(Integer userId, SensorControlVo sensorControlVo) throws ValidException;

    Integer deleteSensorControl(Integer userId, Integer id);

    Integer updateSensorControl(Integer userId, SensorControlVo sensorControlVo);

    MyPage<SensorControlVo> selectSensorControlList(Integer userId, SensorControlVo sensorControlVo);

    SensorControlVo selectSensorControlById(Integer userId, Integer id);
    /**
     * 下发传感器控制命令
     */
    void controlSensor(Integer userId, Integer id) throws ValidException;
}
