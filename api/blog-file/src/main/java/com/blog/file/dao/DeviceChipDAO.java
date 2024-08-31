package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.DeviceChip;
import com.blog.common.entity.file.UserDevice;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lxk
 * @description 用户设备单片机编码映射表DAO层
 * @date 2024/08/29
 */

@Mapper
public interface DeviceChipDAO extends BaseMapper<DeviceChip> {

}
