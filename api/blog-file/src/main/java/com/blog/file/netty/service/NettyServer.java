package com.blog.file.netty.service;


import com.alibaba.fastjson.JSONObject;
import com.blog.file.netty.dto.NettyMessageRetry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: Netty服务端
 * @Author: 308501
 * @date 2024/1/6 15:15
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyServer implements CommandLineRunner {

    private Channel channel;
    // boss事件轮询线程组，处理连接事件
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    // worker事件轮询线程组，用于数据处理
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final NettyServerInitializer nettyServerInitializer;

    // netty 消息重发缓存
    public static final Map<String, NettyMessageRetry> retryMap = new ConcurrentHashMap<>();

    @Value("${netty.port}")
    private Integer port;

    /**
     * 开启Netty服务
     */
    @Override
    public void run(String... args) {
        try {
            // 启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 设置参数，组配置
            serverBootstrap.group(bossGroup, workerGroup)
                    // 指定channel
                    .channel(NioServerSocketChannel.class)
                    // 初始化服务端可连接队列
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 允许重复使用本地地址和端口，连接关闭后，可以立即重用端口
                    .option(ChannelOption.SO_REUSEADDR, true)
                    // 设置TCP长连接，TCP会主动探测空闲连接的有效性
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 禁用Nagle算法，小数据时可以即时传输
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 发送缓冲区大小
                    .childOption(ChannelOption.SO_SNDBUF, 256 * 1024)
                    // 接收缓冲区大小
                    .childOption(ChannelOption.SO_RCVBUF, 256 * 1024)
                    // Netty服务端channel初始化
                    .childHandler(nettyServerInitializer);
            // 绑定端口，开始接收进来的连接
            ChannelFuture future = serverBootstrap.bind(port).sync();
            if (future.isSuccess()) {
                log.info("Netty服务端启动!! 端口:[{}]", port);
            }
            channel = future.channel();
        } catch (Exception e) {
            log.error("Netty服务端启动异常!! error:{}", e.getMessage());
        }
    }

    @PreDestroy
    private void destroy() {
        if (channel != null) {
            channel.close();
        }
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.warn("Netty服务关闭!!");
    }

    public boolean channelWriteByChannelId(ChannelId channelId, String msg) {
        return channelWriteByChannelId(channelId, msg, true);
    }

    /**
     * netty消息发送
     * @param channelId 通道Id
     * @param msg 发送消息
     * @param retry 是否重发 是：true
     * @return 消息是否发送成功
     */
    public boolean channelWriteByChannelId(ChannelId channelId, String msg, boolean retry) {
        ChannelHandlerContext ctx = NettyServerHandler.channelMap.get(channelId);
        if (ctx == null) {
            log.warn("通道【{}】不存在!!", channelId);
            return false;
        }
        ctx.writeAndFlush(msg);
        if (retry) {
            addNettyRetryMap(channelId, msg);
        }
        return true;
    }

    public boolean channelWriteByClientId(String registerId, String msg) {
        ChannelId channelId = NettyServerHandler.clientMap.get(registerId).getChannelId();
        if (channelId != null) {
            return channelWriteByChannelId(channelId, msg);
        }
        return false;
    }

    private void addNettyRetryMap(ChannelId channelId, String msg) {
        JSONObject jsonObject = (JSONObject) JSONObject.parse(msg);
        jsonObject.get("requestId");
        retryMap.put(jsonObject.get("requestId").toString(), new NettyMessageRetry(channelId, msg, new Date(), 0));
    }

    public void removeNettyRetryMap(String requestId) {
        retryMap.remove(requestId);
    }


    public boolean close(ChannelId channelId) {
        ChannelHandlerContext ctx = NettyServerHandler.channelMap.get(channelId);
        if (ctx == null) {
            log.warn("通道【{}】不存在!!", channelId);
            return false;
        }
        NettyServerHandler.channelMap.remove(channelId);
        ctx.close();
        return true;
    }
}
