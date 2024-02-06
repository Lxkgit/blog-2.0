package com.blog.file.service;

import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.file.vo.SensorControlVo;
import com.blog.common.entity.file.vo.SensorDataVo;
import com.blog.common.util.MyPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lxk
 * @description 传感器数据服务类
 * @date 2024/02/05
 */

@Service
public interface SensorDataService {

    Integer saveSensorData(SensorData sensorData);

    MyPage<SensorDataVo> selectSensorDataList(Integer userId, SensorDataVo sensorDataVo);
}
