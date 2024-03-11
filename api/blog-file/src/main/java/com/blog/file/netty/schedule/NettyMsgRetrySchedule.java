package com.blog.file.netty.schedule;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.constant.Constant;
import com.blog.file.netty.dto.NettyMessageRetry;
import com.blog.file.netty.service.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * @description: netty消息重发类
 * @Author: 308501
 * @date 2024/3/11 15:50
 */

@Slf4j
@Configuration     //证明这个类是一个配置文件
@EnableScheduling  //启用定时器
public class NettyMsgRetrySchedule {

    @Resource
    private NettyServer nettyServer;

    /**
     * Netty 消息重发 五分钟检测一次 重发需要重发但五分钟内未收到响应的消息
     * 每五分钟检测一次
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void updateDeviceStatus() {
        Date date = new Date();
        for (Map.Entry<String, NettyMessageRetry> entry : NettyServer.retryMap.entrySet()) {

            NettyMessageRetry value = entry.getValue();
            long interval = (date.getTime() - value.getDate().getTime()) / 1000;
            // 删除重发太多次的消息
            if (value.getRetryCount() > Constant.NETTY_MSG_RETRY_COUNT) {
                JSONObject jsonObject = (JSONObject) JSONObject.parse(value.getMsg());
                nettyServer.removeNettyRetryMap(jsonObject.getString("requestId"));
            }
            // 五分钟没收到响应的数据重新发送
            if (interval > Constant.NETTY_MSG_RETRY) {
                // 已经是重发的消息不需要再次放入重发队列
                nettyServer.channelWriteByChannelId(value.getChannelId(), value.getMsg(), false);
                // 重发次数加一
                value.setRetryCount(value.getRetryCount() + 1);
            }
        }
    }
}
