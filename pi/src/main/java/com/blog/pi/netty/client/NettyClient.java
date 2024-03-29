package com.blog.pi.netty.client;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.pi.config.InitConfig;
import com.blog.pi.dao.RegisterSettingDAO;
import com.blog.pi.entity.RegisterSetting;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:51
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyClient implements CommandLineRunner {

    private Channel channel;
    private final EventLoopGroup workGroup = new NioEventLoopGroup();
    private final NettyClientInitializer nettyClientInitializer;


    @Resource
    private RegisterSettingDAO registerSettingDAO;

    @Override
    public void run(String... args) {
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    // 设置TCP长连接，TCP会主动探测空闲连接的有效性
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    // 禁用Nagle算法，小数据时可以即时传输
                    .option(ChannelOption.TCP_NODELAY, true)
                    // 发送缓冲区大小
                    .option(ChannelOption.SO_SNDBUF, 256 * 1024)
                    // 接收缓冲区大小
                    .option(ChannelOption.SO_RCVBUF, 256 * 1024)
                    // Netty客户端channel初始化
                    .handler(nettyClientInitializer);
            // 连接服务器ip、端口
            ChannelFuture future = bootstrap.connect((String) InitConfig.getRegisterConfig("netty", "ip"),
                    (Integer) InitConfig.getRegisterConfig("netty", "port"));

            //客户端断线重连逻辑
            future.addListener((ChannelFutureListener) futureListener -> {
                if (futureListener.isSuccess()) {
                    log.info("连接Netty服务端成功!!");
                } else {
                    log.warn("连接Netty服务端失败，准备30s后进行断线重连!!");
                    futureListener.channel().eventLoop().schedule((Runnable) this::run, 30, TimeUnit.SECONDS);
                }
            });
            channel = future.channel();
        } catch (Exception e) {
            log.error("连接Netty服务端异常!! error:{}", e.getMessage());
        }
    }

    @PreDestroy
    private void destroy() {
        if (channel != null) {
            channel.close();
        }
        workGroup.shutdownGracefully();
        log.warn("Netty连接关闭!!");
    }

    public boolean sendMsg(String msg) {
        boolean active = channel.isActive();
        if (active) {
            channel.writeAndFlush(msg);
        } else {
            log.warn("channel active:{}", false);
        }
        return active;
    }
}

