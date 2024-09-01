package com.blog.file.netty.listener;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.Device;
import com.blog.common.entity.file.DeviceHeartbeat;
import com.blog.common.entity.file.UserDevice;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.dao.DeviceHeartbeatDAO;
import com.blog.file.dao.UserDeviceDAO;
import com.blog.file.feign.UserClient;
import com.blog.file.netty.dto.NettyClientChannel;
import com.blog.file.netty.dto.heart.NettyHeartBeatDto;
import com.blog.file.netty.dto.register.NettyRegisterDto;
import com.blog.file.netty.enums.NettyPacketType;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.event.NettyPacketEvent;
import com.blog.file.netty.service.NettyDeviceService;
import com.blog.file.netty.service.NettyFileSync;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.netty.service.NettyServerHandler;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @description: Netty服务端自定义数据包处理监听器
 * @Author: lxk
 * @date 2024/1/6 15:17
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServerPacketListener implements ApplicationListener<NettyPacketEvent> {

    private final NettyServer nettyServer;

    @Resource
    private UserClient userClient;

    @Resource
    private DeviceDAO deviceDAO;

    @Resource
    private NettyDeviceService nettyDeviceData;

    @Resource
    private NettyFileSync nettyFileSync;

    @Resource
    private UserDeviceDAO userDeviceDAO;

    @Resource
    private DeviceHeartbeatDAO deviceHeartbeatDAO;


    @Async
    @Override
    public void onApplicationEvent(NettyPacketEvent event) {
        ChannelId channelId = (ChannelId) event.getSource();
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        String requestId = event.getNettyPacket().getRequestId();
        String topic = event.getNettyPacket().getTopic();
        String username = event.getNettyPacket().getUsername();
        String deviceCode = event.getNettyPacket().getDeviceCode();
        String data = event.getNettyPacket().getData().toString();
        log.info("channelId:【{}】 nettyPacketType:【{}】 topic:【{}】 username:【{}】 deviceCode:【{}】 data:【{}】",
                channelId, nettyPacketType, topic, username, deviceCode, data);
        if (nettyPacketType.equals(NettyPacketType.REGISTER.getValue())) {

            // netty设备注册 单片机 传感器注册流程
            QueryWrapper<UserDevice> userDeviceQueryWrapper = new QueryWrapper<>();
            userDeviceQueryWrapper.eq("username", username).eq("device_code", deviceCode);
            UserDevice selectDevice = userDeviceDAO.selectOne(userDeviceQueryWrapper);
            // 设备编码错误拒绝注册
            if (selectDevice == null) {
                log.error("用户与编码匹配失败，拒绝连接");
                nettyServer.close(channelId);
            } else {
                // netty 设备通道绑定 后续发送消息获取通道
                NettyRegisterDto nettyRegisterDto = JSONObject.parseObject(data, NettyRegisterDto.class);
                if (!NettyServerHandler.clientMap.containsKey(deviceCode)) {
                    addNettyChannel(channelId, username, deviceCode);
                    log.info("注册 客户端【{}】与netty通道【{}】绑定", deviceCode, channelId);
                }

                // 创建设备 写入数据
                Device device = new Device();
                device.setUsername(username);
                device.setDeviceName(nettyRegisterDto.getDeviceName());
                device.setDeviceCode(deviceCode);
                device.setDataJson(JSONObject.toJSONString(data));
                device.setUpdateTime(new Date());
                device.setDeviceStatus(1);
                device.setMemo(nettyRegisterDto.getMemo());

                // 当前设备未注册 首次注册创建设备
                if (selectDevice.getCodeStatus() == 0) {
                    device.setCreateTime(new Date());
                    deviceDAO.insert(device);

                    // 将设备状态修改为已注册
                    UserDevice userDevice = new UserDevice();
                    userDevice.setId(selectDevice.getId());
                    userDevice.setCodeStatus(1);
                    userDevice.setUpdateTime(new Date());
                    userDeviceDAO.updateById(userDevice);
                } else {

                    // 当前设备已注册 更新设备数据
                    QueryWrapper<Device> wrapper = new QueryWrapper<>();
                    wrapper.eq("username", username).eq("device_code", deviceCode);
                    deviceDAO.update(device, wrapper);
                }

            }

        } else if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            // 记录的通道数据丢失 由心跳恢复通道数据
            if (!NettyServerHandler.clientMap.containsKey(deviceCode)) {
                addNettyChannel(channelId, username, deviceCode);
                log.info("心跳 客户端【{}】与netty通道【{}】绑定", deviceCode, channelId);
            }

            // 更新通道最近心跳时间 防止被定时任务清除通道
            NettyServerHandler.clientMap.get(deviceCode).setDate(new Date());
            NettyHeartBeatDto nettyHeartBeat = JSONObject.parseObject(data, NettyHeartBeatDto.class);

            // 记录心跳中携带的 cpu 内存 网络 状态数据
            DeviceHeartbeat deviceHeartbeat = new DeviceHeartbeat();
            deviceHeartbeat.setUsername(username);
            deviceHeartbeat.setDeviceCode(deviceCode);
            deviceHeartbeat.setDeviceJson(JSONObject.toJSONString(data));
            deviceHeartbeat.setCreateTime(nettyHeartBeat.getHeartBeat());
            deviceHeartbeatDAO.insert(deviceHeartbeat);


        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
//            BlogUser blogUser = JSONObject.parseObject(JSONObject.toJSONString(userClient.getUserByUsername(username).getResult()), BlogUser.class);
//            JSONObject jsonObject = (JSONObject) event.getNettyPacket().getData();

            // 处理单片机、传感器注册数据
            if (topic.equals(NettyTopicEnum.CHIP_SENSOR_REGISTER.getTopic())) {
                nettyDeviceData.chipAndSensorRegister(data, deviceCode);
            } else if (topic.equals(NettyTopicEnum.SENSOR_DATA.getTopic())) {
                nettyDeviceData.receiveSensorData(data, deviceCode);
            }


//            // 对客户端请求的响应
//            // 收到传感器数据回复响应
//            if (topic.equals(NettyTopicEnum.BLOG_SENSOR_DATA.getTopic())) {
//                nettyDeviceData.SensorData(jsonObject, blogUser, channelId, topic, username, deviceCode);
//            }
//            if (topic.equals(NettyTopicEnum.BLOG_SENSOR_CONTROL.getTopic())) {
//                nettyDeviceData.SensorControl();
//            }
//            if (topic.equals(NettyTopicEnum.BLOG_FILE_SYNC.getTopic())) {
////                nettyFileSync
//            }

        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            log.info("channelId:{} RESPONSE!! data:{}", channelId, JSONObject.toJSONString(event.getNettyPacket().getData()));
            nettyServer.removeNettyRetryMap(requestId);
        }
    }

    private void addNettyChannel(ChannelId channelId, String username, String registerId) {
        NettyServerHandler.clientMap.put(registerId, new NettyClientChannel(channelId, registerId, username, new Date()));
//        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
//        deviceQueryWrapper.eq("username", username);
//        deviceQueryWrapper.eq("device_code", registerId);
//        Device deviceStatus = new Device();
//        deviceStatus.setDeviceStatus(Constant.DEVICE_ONLINE);
//        deviceDAO.update(deviceStatus, deviceQueryWrapper);
    }

}

