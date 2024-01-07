package com.blog.pi.netty.listener;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blog.pi.netty.dto.NettyPacket;
import com.blog.pi.netty.enums.NettyPacketType;
import com.blog.pi.netty.event.NettyPacketEvent;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyClientPacketListener implements ApplicationListener<NettyPacketEvent> {

    @Async
    @Override
    public void onApplicationEvent(NettyPacketEvent event) {
        ChannelId channelId = (ChannelId) event.getSource();
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            log.info("client receive heartbeat!!");
        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
            // TODO REQUEST
            log.info("channelId:{} REQUEST!! data:{}", channelId, JSONObject.toJSONString(event.getNettyPacket().getData()));
        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            NettyPacket<Object> nettyResponse = JSONObject.parseObject(JSONObject.toJSONString(event.getNettyPacket()), new TypeReference<NettyPacket<Object>>() {
            }.getType());
            NettyPacket.response(event.getNettyPacket().getRequestId(), nettyResponse);
        } else {
            log.warn("unknown NettyPacketType!! channelId:{} event:{}", channelId, JSONObject.toJSONString(event));
        }
    }
}
