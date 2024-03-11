package com.blog.file.netty.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.file.Device;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.netty.dto.NettyClientChannel;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.netty.service.NettyServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @description: 设备状态更新
 * @Author: 308501
 * @date 2024/1/29 19:17
 */
@Slf4j
@Configuration     //证明这个类是一个配置文件
@EnableScheduling  //启用定时器
public class DeviceStatusSchedule {

    @Resource
    private DeviceDAO deviceDAO;

    /**
     * 正常netty客户端在离线都会有逻辑处理，如果服务器异常断电可能netty离线无法收到，需要定时检测
     * 定时检测设备在离线，更新状态，推送页面
     * 每三分钟检测一次
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void updateDeviceStatus() {
        Date date = new Date();
        for (Map.Entry <String, NettyClientChannel>  entry : NettyServerHandler.clientMap.entrySet()) {
            NettyClientChannel channel = entry.getValue();
            long interval = (date.getTime() - channel.getDate().getTime())/1000;
            // 设备最近的心跳如果在三分钟之前则说明设备已经离线 移除数据
            if (interval > Constant.DEVICE_WAIT_TIME) {
                removeChannelByRegisterId(channel.getRegisterId(), deviceDAO);
            }
            // 再次检测通道数据
            NettyServerHandler.channelMap.keySet().removeIf(key -> !containChannelId(key));
            log.info("channelId: 连接通道数量:{}, client: 绑定通道数量:{}", NettyServerHandler.channelMap.size(), NettyServerHandler.clientMap.size());
        }
    }

    /**
     * 判断 clientMap 中是否包含指定 channelId
     * @param channelId
     * @return
     */
    public static boolean containChannelId(ChannelId channelId) {
        for (Map.Entry <String, NettyClientChannel>  entry : NettyServerHandler.clientMap.entrySet()) {
            NettyClientChannel channel = entry.getValue();
            if (channel.getChannelId().equals(channelId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据 channelId 客户端获取注册编码
     * @param channelId
     * @return
     */
    public static String getChannelRegisterIdByChannelId(ChannelId channelId) {
        for (Map.Entry <String, NettyClientChannel>  entry : NettyServerHandler.clientMap.entrySet()) {
            NettyClientChannel channel = entry.getValue();
            if (channel.getChannelId().equals(channelId)) {
                return channel.getRegisterId();
            }
        }
        return "";
    }

    /**
     * 根据客户端注册编码删除通道
     * @param registerId
     * @param deviceDAO
     */
    public static void removeChannelByRegisterId(String registerId, DeviceDAO deviceDAO) {
        for (Map.Entry <String, NettyClientChannel>  entry : NettyServerHandler.clientMap.entrySet()) {
            NettyClientChannel channel = entry.getValue();
            if (channel.getRegisterId().equals(registerId)) {
                removeNettyChannel(entry, channel, deviceDAO);
                log.info("netty通道 username:【{}】 registerId:【{}】 已离线", channel.getUsername(), registerId);
            }
        }
    }

    public static void removeNettyChannel(Map.Entry<String, NettyClientChannel> entry, NettyClientChannel channel, DeviceDAO deviceDAO) {
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("username", channel.getUsername());
        deviceQueryWrapper.eq("device_code", channel.getRegisterId());
        Device deviceStatus = new Device();
        deviceStatus.setDeviceStatus(Constant.DEVICE_OFFLINE);
        deviceDAO.update(deviceStatus, deviceQueryWrapper);
        NettyServerHandler.channelMap.get(entry.getValue().getChannelId()).close();
        NettyServerHandler.clientMap.remove(entry.getKey());
        NettyServerHandler.channelMap.remove(entry.getValue().getChannelId());
    }
}