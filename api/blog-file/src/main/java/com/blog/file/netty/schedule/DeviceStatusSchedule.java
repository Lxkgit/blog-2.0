package com.blog.file.netty.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.constant.Constant;
import com.blog.common.entity.file.Device;
import com.blog.file.dao.DeviceDAO;
import com.blog.file.netty.dto.NettyClientChannel;
import com.blog.file.netty.service.NettyServerHandler;
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
    @Scheduled(cron = "* 0/3 * * * ?")
    public void updateDeviceStatus() {
        Date date = new Date();
        for (Map.Entry <String, NettyClientChannel>  entry : NettyServerHandler.clientMap.entrySet()) {
            NettyClientChannel channel = entry.getValue();
            long interval = (date.getTime() - channel.getDate().getTime())/1000;
            // 设备最近的心跳如果在三分钟之前则说明设备已经离线 移除数据
            if (interval > Constant.DEVICE_WAIT_TIME) {
                QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username", channel.getUsername());
                queryWrapper.eq("device_code", channel.getRegisterId());
                Device device = new Device();
                device.setDeviceStatus(Constant.DEVICE_OFFLINE);
                deviceDAO.update(device, queryWrapper);

                NettyServerHandler.channelMap.remove(channel.getChannelId());
                NettyServerHandler.clientMap.remove(entry.getKey());
            }
        }
    }
}