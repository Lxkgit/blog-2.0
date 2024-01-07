package com.blog.file.netty.listener;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.dto.request.RequestTestVO;
import com.blog.file.netty.dto.response.ResponseTestVO;
import com.blog.file.netty.enums.NettyPacketType;
import com.blog.file.netty.event.NettyPacketEvent;
import com.blog.file.netty.service.NettyServer;
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
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            log.info("server receive heartbeat!! channelId:{}", channelId);
            NettyPacket<String> nettyResponse = NettyPacket.buildResponse("server receive heartbeat " + new Date().toString());
            nettyResponse.setNettyPacketType(NettyPacketType.HEARTBEAT.getValue());
            boolean success = nettyServer.channelWrite(channelId, JSONObject.toJSONString(nettyResponse));
        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
            String command = event.getNettyPacket().getCommand();
            Object data = event.getNettyPacket().getData();
            if (command.equals(NettyPacket.REQUEST_TEST_1)) {
                requestTest1(channelId, event.getNettyPacket().getRequestId(), JSONObject.parseObject(JSONObject.toJSONString(data), new TypeReference<RequestTestVO>() {
                }.getType()));
            } else {
                log.warn("unknown command!! channelId:{} data:{}", channelId, JSONObject.toJSONString(data));
            }
        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            // TODO RESPONSE
            log.info("channelId:{} RESPONSE!! data:{}", channelId, JSONObject.toJSONString(event.getNettyPacket().getData()));
        } else {
            log.warn("unknown NettyPacketType!! channelId:{} event:{}", channelId, JSONObject.toJSONString(event));
        }
    }

    /**
     * 处理请求:RequestTest1
     */
    private void requestTest1(ChannelId channelId, String requestId, RequestTestVO param) {
        log.info("处理客户端【{}】的请求，请求ID:{}，请求参数:{}", channelId, requestId, JSONObject.toJSONString(param));
        ResponseTestVO response = ResponseTestVO.builder().message("server receive param").date(new Date()).build();
        NettyPacket<Object> nettyResponse = NettyPacket.buildResponse(requestId, response);
        nettyServer.channelWrite(channelId, JSONObject.toJSONString(nettyResponse));
    }
}

