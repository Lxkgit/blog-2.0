package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.ChipSensor;
import com.blog.common.entity.file.DeviceChip;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lxk
 * @description 用户单片机传感器编码映射表DAO层
 * @date 2024/08/29
 */

@Mapper
public interface ChipSensorDAO extends BaseMapper<ChipSensor> {

}
