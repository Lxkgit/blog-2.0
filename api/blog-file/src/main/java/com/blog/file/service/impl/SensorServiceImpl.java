package com.blog.file.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.Sensor;
import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.file.vo.SensorVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;
import com.blog.file.dao.SensorDAO;
import com.blog.file.dao.SensorDataDAO;
import com.blog.file.service.SensorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 传感器服务业务层
 * @Author: 308501
 * @date 2024/1/30 20:08
 */

@Slf4j
@Service
public class SensorServiceImpl implements SensorService {

    @Resource
    private SensorDAO sensorDAO;

    @Resource
    private SensorDataDAO sensorDataDAO;

    @Override
    public Integer addSensor(Integer userId, SensorVo sensorVo) throws ValidException {

        return null;
    }

    @Override
    public Integer deleteSensors(Integer userId, String ids) {
        return null;
    }

    @Override
    public Integer updateSensor(Integer userId, SensorVo sensorVo) throws ValidException {
        return null;
    }

    @Override
    public MyPage<SensorVo> selectSensorList(Integer userId, SensorVo sensorVo) {
        return null;
    }

    @Override
    public SensorVo selectSensorId(Integer userId, Integer id) {
        return null;
    }

}
