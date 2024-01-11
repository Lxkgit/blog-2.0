package com.blog.file.netty.service;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.event.NettyPacketEvent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Netty服务端处理器
 * @Author: 308501
 * @date 2024/1/6 15:16
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 全局map，保存通道编码与客户端编码 （用户服务端监听到客户端断开连接，移除map通道）
    public static final Map<ChannelId, ChannelHandlerContext> channelMap = new ConcurrentHashMap<>();

    // 全局map，保存客户端编码与netty通道编码 （用于服务端指定客户端发送消息）
    public static final Map<String, ChannelId> clientMap = new ConcurrentHashMap<>();

    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 当客户端主动连接服务端，通道活跃后触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();
        int clientPort = inetSocketAddress.getPort();
        // 获取连接通道唯一标识
        ChannelId channelId = ctx.channel().id();
        // 如果map中不包含此连接，就保存连接
        if (channelMap.containsKey(channelId)) {
            log.info("客户端【{}】是连接状态，连接通道数量:{}", channelId, channelMap.size());
        } else {
            // 保存连接
            channelMap.put(channelId, ctx);
            log.info("客户端【{}】连接Netty服务端!![clientIp:{} clientPort:{}]", channelId, clientIp, clientPort);
            log.info("连接通道数量:{}", channelMap.size());
        }
    }

    /**
     * 当客户端主动断开连接，通道不活跃触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = inetSocketAddress.getAddress().getHostAddress();
        int clientPort = inetSocketAddress.getPort();
        // 获取终止连接的客户端ID
        ChannelId channelId = ctx.channel().id();
        // 包含此客户端才去删除
        if (channelMap.containsKey(channelId)) {
            // 删除连接
            channelMap.remove(channelId);
            if (clientMap.containsValue(channelId)) {
                Collection<ChannelId> values = clientMap.values();
                values.remove(channelId);
            }
            log.warn("客户端【{}】断开Netty连接!![clientIp:{} clientPort:{}]", channelId, clientIp, clientPort);
            log.info("channelId: 连接通道数量:{}, client: 绑定通道数量:{}", channelMap.size(), clientMap.size());
        }
    }

    /**
     * 通道有消息触发
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
            NettyPacket<String> nettyResponse = NettyPacket.buildRequest("报文解析失败!!");
            ctx.writeAndFlush(JSONObject.toJSONString(nettyResponse));
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        String socketString = ctx.channel().remoteAddress().toString();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.warn("Client: 【{}】 READER_IDLE 读超时", socketString);
                ctx.close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.warn("Client: 【{}】 WRITER_IDLE 写超时", socketString);
                ctx.close();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.warn("Client: 【{}】 ALL_IDLE 读/写超时", socketString);
                ctx.close();
            }
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
}
