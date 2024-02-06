package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.Sensor;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @description: 传感器数据层
 * @Author: 308501
 * @date 2024/1/30 20:07
 */

public interface SensorDAO extends BaseMapper<Sensor> {

    void updateSensorStatusByIds(@Param("ids") Set<String> idSet, @Param("userId") Integer userId, @Param("sensorStatus") Integer sensorStatus);
}
