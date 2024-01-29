package com.blog.pi.netty.client;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blog.pi.config.InitConfig;
import com.blog.pi.netty.dto.NettyPacket;
import com.blog.pi.netty.enums.NettyPacketType;
import com.blog.pi.netty.event.NettyPacketEvent;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:52
 */
@Slf4j
@Component
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class NettyClientHandler extends ChannelDuplexHandler {

    @Lazy
    @Resource
    private NettyClient nettyClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 客户端连接到服务端后调用
     * 可在此次发送客户端注册
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("建立Netty连接!!");
        // 发送注册消息
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", InitConfig.getRegisterConfig("netty", "username"));
        jsonObject.put("registerId", InitConfig.getRegisterConfig("netty", "registerId"));
        NettyPacket<String> nettyRequest = NettyPacket.buildRequest(jsonObject.toJSONString());
        nettyRequest.setNettyPacketType(NettyPacketType.REGISTER.getValue());
        nettyRequest.setTopic(NettyPacketType.REGISTER.getValue());
        ctx.writeAndFlush(JSONObject.toJSONString(nettyRequest));
    }

    /**
     * 关闭连接时
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.warn("Netty连接关闭!!");
        reconnect(ctx);
    }

    /**
     * 心跳处理，每5秒发送一次心跳请求
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                // 向服务端发送心跳包
                NettyPacket<String> nettyRequest = NettyPacket.buildRequest("client heartbeat " + new Date());
                nettyRequest.setNettyPacketType(NettyPacketType.HEARTBEAT.getValue());
                nettyRequest.setTopic(NettyPacketType.HEARTBEAT.getValue());
                // 发送心跳消息，并在发送失败时关闭该连接
                ctx.writeAndFlush(JSONObject.toJSONString(nettyRequest));
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 收到服务端消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            // 报文解析处理
            NettyPacket<Object> nettyPacket = JSONObject.parseObject(msg.toString(), new TypeReference<NettyPacket<Object>>() {
            }.getType());
            // 发布自定义Netty数据包处理事件
            applicationEventPublisher.publishEvent(new NettyPacketEvent(ctx.channel().id(), nettyPacket));
        } catch (Exception e) {
            log.error("channelId:【{}】 报文解析失败!! msg:{} error:{}", ctx.channel().id(), msg.toString(), e.getMessage());
            NettyPacket<String> nettyResponse = NettyPacket.buildRequest("报文解析失败!!");
            ctx.writeAndFlush(JSONObject.toJSONString(nettyResponse));
        }
    }

    /**
     * 当连接发生异常时触发
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        ctx.close();
    }

    private void reconnect(ChannelHandlerContext ctx) {
        log.info("准备30s后断线重连!!");
        ctx.channel().eventLoop().schedule(() -> nettyClient.run(), 30, TimeUnit.SECONDS);
    }
}
