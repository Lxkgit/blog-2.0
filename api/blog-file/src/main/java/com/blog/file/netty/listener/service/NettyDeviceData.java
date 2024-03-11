package com.blog.file.netty.listener.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.file.*;
import com.blog.common.entity.user.BlogUser;
import com.blog.file.dao.ChipDAO;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.dao.SensorDAO;
import com.blog.file.dao.SensorDataDAO;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.service.NettyServer;
import io.netty.channel.ChannelId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description: 处理Netty收到的传感器数据服务
 * @Author: 308501
 * @date 2024/3/11 10:52
 */

@Service
public class NettyDeviceData {

    @Resource
    private NettyServer nettyServer;

    @Resource
    private DeviceDAO deviceDAO;

    @Resource
    private ChipDAO chipDAO;

    @Resource
    private SensorDAO sensorDAO;

    @Resource
    private SensorDataDAO sensorDataDAO;

    public void SensorData(JSONObject jsonObject, BlogUser blogUser, ChannelId channelId, String topic, String username, String registerId) {
        SensorData sensorData = new SensorData();
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("user_id", blogUser.getId());
        deviceQueryWrapper.eq("device_code", registerId);
        Device device = deviceDAO.selectOne(deviceQueryWrapper);
        String chipType = (String) jsonObject.get(Constant.CHIP_TYPE);
        String sensorType = (String) jsonObject.get(Constant.SENSOR_TYPE);
        QueryWrapper<Chip> chipQueryWrapper = new QueryWrapper<>();
        chipQueryWrapper.eq("user_id", blogUser.getId());
        chipQueryWrapper.eq("device_id", device.getId());
        chipQueryWrapper.eq("chip_code", chipType);
        Chip chip = chipDAO.selectOne(chipQueryWrapper);
        QueryWrapper<Sensor> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", blogUser.getId());
        wrapper.eq("chip_id", chip.getId());
        wrapper.eq("sensor_code", sensorType);
        Sensor sensor = sensorDAO.selectOne(wrapper);
        sensorData.setSensorId(sensor.getId());
        sensorData.setSensorData(jsonObject.getString("data"));
        sensorData.setCreateTime(new Date());
        sensorDataDAO.insert(sensorData);

        // 消息响应
        NettyPacket<String> nettyResponse = NettyPacket.buildResponse(registerId, "service receive data");
        nettyResponse.setTopic(topic);
        nettyResponse.setUsername(username);
        nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
    }

    public void SensorControl() {

    }
}
