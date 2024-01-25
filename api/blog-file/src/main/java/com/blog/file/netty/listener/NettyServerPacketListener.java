package com.blog.file.netty.listener;


import com.alibaba.fastjson.JSONObject;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.enums.NettyPacketType;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.event.NettyPacketEvent;
import com.blog.file.netty.service.NettyServer;
import com.blog.file.netty.service.NettyServerHandler;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

    @Async
    @Override
    public void onApplicationEvent(NettyPacketEvent event) {
        ChannelId channelId = (ChannelId) event.getSource();
        String sender = event.getNettyPacket().getSender();
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        String topic = event.getNettyPacket().getTopic();
        String requestId = event.getNettyPacket().getRequestId();
        String data = JSONObject.toJSONString(event.getNettyPacket().getData());
        log.info("channelId:【{}】 sender:【{}】 requestId:【{}】 topic:【{}】 data:{}", channelId, sender, requestId, topic, data);
        if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            NettyPacket<String> nettyResponse = NettyPacket.buildResponse(event.getNettyPacket().getRequestId(), "service receive heartbeat");
            nettyResponse.setNettyPacketType(NettyPacketType.HEARTBEAT.getValue());
            nettyResponse.setTopic(NettyPacketType.HEARTBEAT.getValue());
            nettyResponse.setUserId(0);
            if (!NettyServerHandler.clientMap.containsKey(sender)) {
                NettyServerHandler.clientMap.put(sender, channelId);
                log.info("客户端【{}】与netty通道【{}】绑定", sender, channelId);
            }
            nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
            // 对客户端请求的响应
            // 收到传感器数据回复响应
            if (topic.equals(NettyTopicEnum.BLOG_SENSOR_DATA.getTopic())) {
                NettyPacket<String> nettyResponse = NettyPacket.buildResponse(event.getNettyPacket().getRequestId(), "service receive data");
                nettyResponse.setTopic(topic);
                nettyResponse.setUserId(0);
                nettyServer.channelWriteByChannelId(channelId, JSONObject.toJSONString(nettyResponse));
            }

        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            // TODO RESPONSE
            log.info("channelId:{} RESPONSE!! data:{}", channelId, JSONObject.toJSONString(event.getNettyPacket().getData()));
        } else {
            log.warn("unknown NettyPacketType!! channelId:{} event:{}", channelId, JSONObject.toJSONString(event));
        }
    }

}

