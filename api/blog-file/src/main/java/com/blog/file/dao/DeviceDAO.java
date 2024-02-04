package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.Device;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @description: 设备数据层
 * @Author: 308501
 * @date 2024/1/29 13:51
 */

public interface DeviceDAO extends BaseMapper<Device> {

    void updateDeviceStatusById(@Param("id") String id, @Param("userId") Integer userId, @Param("deviceStatus") Integer deviceStatus);
    void updateDeviceStatusByIds(@Param("ids") Set<String> ids, @Param("userId") Integer userId, @Param("deviceStatus") Integer deviceStatus);
}
