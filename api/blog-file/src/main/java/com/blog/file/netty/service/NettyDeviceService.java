package com.blog.file.netty.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.file.*;
import com.blog.common.entity.user.BlogUser;
import com.blog.file.dao.*;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.dto.heart.NettyHeartBeatDto;
import com.blog.file.netty.dto.register.NettyChipRegisterDto;
import com.blog.file.netty.dto.register.NettySensorRegisterDto;
import com.blog.file.netty.dto.sensor.receive.SensorDataDto;
import com.blog.file.redis.RedisUtil;
import io.netty.channel.ChannelId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description: 处理Netty收到的传感器数据服务
 * @Author: lxk
 * @date 2024/3/11 10:52
 */

@Service
public class NettyDeviceService {

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

    @Resource
    private DeviceChipDAO deviceChipDAO;

    @Resource
    private ChipSensorDAO chipSensorDAO;

    /**
     * 传感器设备主动注册
     * @param data
     * @param deviceCode
     */
    public void chipAndSensorRegister(String data, String deviceCode) {
        NettyChipRegisterDto nettyChipRegisterDto = JSONObject.parseObject(data, NettyChipRegisterDto.class);

        QueryWrapper<DeviceChip> chipQueryWrapper = new QueryWrapper<>();
        chipQueryWrapper.eq("device_code", deviceCode).eq("chip_code", nettyChipRegisterDto.getChipCode());
        DeviceChip selectChip = deviceChipDAO.selectOne(chipQueryWrapper);
        if (selectChip != null) {
            Chip chip = new Chip();
            chip.setDeviceCode(deviceCode);
            chip.setChipCode(nettyChipRegisterDto.getChipCode());
            chip.setChipName(nettyChipRegisterDto.getChipName());
            chip.setChipType(nettyChipRegisterDto.getChipType());
            chip.setMemo(nettyChipRegisterDto.getMemo());
            chip.setUpdateTime(new Date());

            DeviceChip deviceChip = new DeviceChip();
            deviceChip.setId(selectChip.getId());
            deviceChip.setUpdateTime(new Date());

            if (selectChip.getCodeStatus() == 0) {
                chip.setCreateTime(new Date());
                chipDAO.insert(chip);

                deviceChip.setCodeStatus(1);
            } else {
                QueryWrapper<Chip> wrapper = new QueryWrapper<>();
                wrapper.eq("device_code", deviceCode).eq("chip_code", nettyChipRegisterDto.getChipCode());
                chipDAO.update(chip, wrapper);
            }
            deviceChipDAO.updateById(deviceChip);

            for (NettySensorRegisterDto nettySensorRegisterDto : nettyChipRegisterDto.getSensorList()) {
                QueryWrapper<ChipSensor> sensorQueryWrapper = new QueryWrapper<>();
                sensorQueryWrapper.eq("chip_code", selectChip.getChipCode()).eq("sensor_code", nettySensorRegisterDto.getSensorCode());
                ChipSensor selectSensor = chipSensorDAO.selectOne(sensorQueryWrapper);
                if (selectSensor != null) {
                    Sensor sensor = new Sensor();
                    sensor.setChipCode(nettyChipRegisterDto.getChipCode());
                    sensor.setSensorName(nettySensorRegisterDto.getSensorName());
                    sensor.setSensorCode(nettySensorRegisterDto.getSensorCode());
                    sensor.setSensorStatus(1);
                    sensor.setSensorType(nettySensorRegisterDto.getSensorType());
                    sensor.setMemo(nettySensorRegisterDto.getMemo());
                    sensor.setUpdateTime(new Date());

                    ChipSensor chipSensor = new ChipSensor();
                    chipSensor.setId(selectSensor.getId());
                    chipSensor.setUpdateTime(new Date());
                    if (selectSensor.getCodeStatus() == 0) {
                        sensor.setCreateTime(new Date());
                        sensorDAO.insert(sensor);

                        chipSensor.setCodeStatus(1);
                    } else {
                        QueryWrapper<Sensor> wrapper = new QueryWrapper<>();
                        wrapper.eq("chip_code", nettyChipRegisterDto.getChipCode())
                                .eq("sensor_code", nettySensorRegisterDto.getSensorCode());
                        sensorDAO.update(sensor, wrapper);
                    }
                    chipSensorDAO.updateById(chipSensor);
                }
            }
        }
    }


    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisUtil redisUtil;

    /**
     * 处理接收到的传感器数据
     * @param data JSON 格式数据
     * @param deviceCode 设备编码
     */
    public void receiveSensorData(String data, String deviceCode) {
        SensorDataDto sensorDataDto = JSONObject.parseObject(data, SensorDataDto.class);

        // 传感器数据
        for (SensorDataDto.ReceiveData receiveData : sensorDataDto.getDataList()) {
            SensorData sensorData = new SensorData();
            sensorData.setDeviceCode(deviceCode);
            sensorData.setChipCode(sensorDataDto.getChipCode());
            sensorData.setMsgCode(sensorDataDto.getMsgCode());
            sensorData.setMsgCount(sensorDataDto.getMsgCount());
            sensorData.setCreateTime(new Date());
            sensorData.setSensorCode(receiveData.getSensorCode());
            sensorData.setSensorData(receiveData.getSensorData());

            sensorDataDAO.insert(sensorData);
        }

        redisUtil.set("add", "asd");

//        redisUtil.expire("test", 80);
    }

//    /**
//     * 处理传感器数据上报消息，并发送消息接收相应
//     *
//     * @param jsonObject 上报消息json格式
//     * @param blogUser 设备所属用户
//     * @param channelId netty消息通道
//     * @param topic netty响应topic
//     * @param username 用户名
//     * @param registerId netty注册id
//     */
//    public void SensorData(JSONObject jsonObject, BlogUser blogUser, ChannelId channelId, String topic, String username, String registerId) {
//        SensorData sensorData = new SensorData();
//        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
//        deviceQueryWrapper.eq("user_id", blogUser.getId());
//        deviceQueryWrapper.eq("device_code", registerId);
//        Device device = deviceDAO.selectOne(deviceQueryWrapper);
//        String chipType = (String) jsonObject.get(Constant.CHIP_TYPE);
//        String sensorType = (String) jsonObject.get(Constant.SENSOR_TYPE);
//        QueryWrapper<Chip> chipQueryWrapper = new QueryWrapper<>();
//        chipQueryWrapper.eq("user_id", blogUser.getId());
//        chipQueryWrapper.eq("device_id", device.getId());
//        chipQueryWrapper.eq("chip_code", chipType);
//        Chip chip = chipDAO.selectOne(chipQueryWrapper);
//        QueryWrapper<Sensor> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id", blogUser.getId());
//        wrapper.eq("chip_id", chip.getId());
//        wrapper.eq("sensor_code", sensorType);
//        Sensor sensor = sensorDAO.selectOne(wrapper);
//        sensorData.setSensorId(sensor.getId());
//        sensorData.setSensorData(jsonObject.getString("data"));
//        sensorData.setCreateTime(new Date());
//        sensorDataDAO.insert(sensorData);
//
//        // 消息响应
//        NettyPacket<String> nettyResponse = NettyPacket.buildResponse(registerId, "service receive data");
//        nettyResponse.setTopic(topic);
//        nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
//    }

    public void SensorControl() {

    }
}
