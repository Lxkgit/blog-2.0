package com.blog.file.service.impl;

import com.blog.common.entity.file.SensorType;
import com.blog.file.dao.SensorTypeDAO;
import com.blog.file.service.SensorTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 传感器类型服务类
 * @Author: 308501
 * @date 2024/2/22 14:55
 */

@Slf4j
@Service
public class SensorTypeServiceImpl implements SensorTypeService {

    @Resource
    private SensorTypeDAO sensorTypeDAO;

    public List<SensorType> selectSensorTypeList() {
        return sensorTypeDAO.selectList(null);
    }
}
