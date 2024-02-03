package com.blog.file.service;

import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.file.vo.SensorVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;

/**
 * @description: 传感器服务类
 * @Author: 308501
 * @date 2024/1/30 20:08
 */

public interface SensorService {


    Integer addSensor(Integer userId, SensorVo sensorVo) throws ValidException;

    Integer deleteSensors(Integer userId, String ids);

    Integer updateSensor(Integer userId, SensorVo sensorVo) throws ValidException;

    MyPage<SensorVo> selectSensorList(Integer userId, SensorVo sensorVo);

    SensorVo selectSensorId(Integer userId, Integer id);

}
