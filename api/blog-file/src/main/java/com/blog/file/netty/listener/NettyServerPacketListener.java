package com.blog.file.netty.listener;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.file.Chip;
import com.blog.common.entity.file.Device;
import com.blog.common.entity.file.Sensor;
import com.blog.common.entity.file.SensorData;
import com.blog.common.entity.user.BlogUser;
import com.blog.file.dao.ChipDAO;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.dao.SensorDAO;
import com.blog.file.dao.SensorDataDAO;
import com.blog.file.feign.UserClient;
import com.blog.file.netty.dto.NettyClientChannel;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.enums.NettyPacketType;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.event.NettyPacketEvent;
import com.blog.file.netty.schedule.DeviceStatusSchedule;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.netty.service.NettyServerHandler;
import com.blog.file.service.SensorService;
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
 * @Author: 308501
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
    private SensorService sensorService;

    @Resource
    private SensorDAO sensorDAO;

    @Resource
    private ChipDAO chipDAO;

    @Resource
    private SensorDataDAO sensorDataDAO;

    @Async
    @Override
    public void onApplicationEvent(NettyPacketEvent event) {
        ChannelId channelId = (ChannelId) event.getSource();
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        String requestId = event.getNettyPacket().getRequestId();
        String topic = event.getNettyPacket().getTopic();
        String username = event.getNettyPacket().getUsername();
        String registerId = event.getNettyPacket().getRegisterId();
        String data = event.getNettyPacket().getData().toString();
        log.info("channelId:【{}】 requestId:【{}】 topic:【{}】 username:【{}】 registerId:【{}】 data:【{}】", channelId, requestId, topic, username, registerId, data);
        if (nettyPacketType.equals(NettyPacketType.REGISTER.getValue())) {
            JSONObject jsonObject = (JSONObject) event.getNettyPacket().getData();
            QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", jsonObject.getString("username"));
            queryWrapper.eq("device_code", jsonObject.getString("registerId"));
            Device device = deviceDAO.selectOne(queryWrapper);
            if (device == null) {
                log.error("用户与编码匹配失败，拒绝连接");
                nettyServer.close(channelId);
            } else {
                if (!NettyServerHandler.clientMap.containsKey(registerId)) {
                    addNettyChannel(channelId, username, registerId);
                    log.info("注册 客户端【{}】与netty通道【{}】绑定", registerId, channelId);
                }
            }
        } else if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            NettyPacket<String> nettyResponse = NettyPacket.buildResponse(event.getNettyPacket().getRequestId(), "service receive heartbeat");
            nettyResponse.setNettyPacketType(NettyPacketType.HEARTBEAT.getValue());
            nettyResponse.setTopic(NettyPacketType.HEARTBEAT.getValue());
            nettyResponse.setUsername(username);
            if (!NettyServerHandler.clientMap.containsKey(registerId)) {
                addNettyChannel(channelId, username, registerId);
                log.info("心跳 客户端【{}】与netty通道【{}】绑定", registerId, channelId);
            }
            NettyServerHandler.clientMap.get(registerId).setDate(new Date());
            nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
            BlogUser blogUser = JSONObject.parseObject(JSONObject.toJSONString(userClient.getUserByUsername(username).getResult()), BlogUser.class);
            // 对客户端请求的响应
            // 收到传感器数据回复响应
            if (topic.equals(NettyTopicEnum.BLOG_SENSOR_DATA.getTopic())) {

                JSONObject jsonObject = (JSONObject) event.getNettyPacket().getData();
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
                sensorDataDAO.insert(sensorData);

                NettyPacket<String> nettyResponse = NettyPacket.buildResponse(event.getNettyPacket().getRequestId(), "service receive data");
                nettyResponse.setTopic(topic);
                nettyResponse.setUsername(username);
                nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
            }

        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            // TODO RESPONSE
            log.info("channelId:{} RESPONSE!! data:{}", channelId, JSONObject.toJSONString(event.getNettyPacket().getData()));
        } else {
            log.warn("unknown NettyPacketType!! channelId:{} event:{}", channelId, JSONObject.toJSONString(event));
        }
    }

    private void addNettyChannel(ChannelId channelId, String username, String registerId) {
        NettyServerHandler.clientMap.put(registerId, new NettyClientChannel(channelId, registerId, username, new Date()));
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("username", username);
        deviceQueryWrapper.eq("device_code", registerId);
        Device deviceStatus = new Device();
        deviceStatus.setDeviceStatus(Constant.DEVICE_ONLINE);
        deviceDAO.update(deviceStatus, deviceQueryWrapper);
    }

}

