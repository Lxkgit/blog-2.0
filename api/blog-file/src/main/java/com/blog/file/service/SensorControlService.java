package com.blog.file.service;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.entity.file.vo.ChipVo;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;

import java.util.List;

/**
 * @description: 传感器控制服务类
 * @Author: lxk
 * @date 2024/2/2 10:34
 */

public interface SensorControlService {

    Integer createSensorControl(Integer userId, SensorControlVo sensorControlVo) throws ValidException, IllegalAccessException, InstantiationException, NoSuchFieldException;

    Integer deleteSensorControl(Integer userId, List<Integer> ids);

    Integer updateSensorControl(Integer userId, SensorControlVo sensorControlVo);

    MyPage<SensorControlVo> selectSensorControlList(Integer userId, SensorControlVo sensorControlVo) throws ValidException;

    JSONObject selectSensorControlById(Integer userId, Integer id);

    /**
     * 下发传感器控制命令
     */
    Boolean controlSensor(Integer userId, Integer id) throws ValidException;
}
