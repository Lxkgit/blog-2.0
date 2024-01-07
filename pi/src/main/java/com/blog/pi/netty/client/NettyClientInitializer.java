package com.blog.pi.netty.client;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:52
 */
@Component
@RequiredArgsConstructor
public class NettyClientInitializer extends ChannelInitializer<Channel> {

    private final NettyClientHandler nettyClientHandler;

    /**
     * 初始化channel
     */
    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                // 解码器，对接收到的数据进行长度字段解码，也会对数据进行粘包和拆包处理
                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2))
                // 编码器，主要是在响应字节数据前面添加字节长度字段
                .addLast(new LengthFieldPrepender(2))
                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                // 心跳检测  多长时间没有触发读事件 多长时间没有触发写事件 多长时间没有触发读写事件 时间单位
                .addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS))
                // 自定义的处理入站出站的 handler
                .addLast(nettyClientHandler);
    }
}

