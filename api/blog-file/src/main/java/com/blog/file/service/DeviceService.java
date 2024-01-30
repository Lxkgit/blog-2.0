package com.blog.file.service;

import com.blog.common.entity.file.Device;
import com.blog.common.entity.file.vo.DeviceVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;

import java.util.List;

/**
 * @description: 下级设备服务类
 * @Author: 308501
 * @date 2024/1/29 13:49
 */

public interface DeviceService {

    Integer addDevice(Integer userId, DeviceVo deviceVo) throws ValidException;

    Integer updateDevice(Integer userId, DeviceVo deviceVo) throws ValidException;

    Integer deleteDevice(Integer userId, String ids) throws ValidException;

    List<Device> selectDeviceList(Integer userId);
}
