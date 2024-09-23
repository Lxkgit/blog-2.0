package com.blog.file.netty.event;


import com.blog.common.netty.dto.NettyPacket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description: 自定义Netty数据包处理事件
 * @Author: lxk
 * @date 2024/1/6 15:15
 */
@Getter
public class NettyPacketEvent extends ApplicationEvent {

    private static final long serialVersionUID = 386562306947250643L;

    private final NettyPacket<Object> nettyPacket;

    public NettyPacketEvent(Object source, NettyPacket<Object> nettyPacket) {
        super(source);
        this.nettyPacket = nettyPacket;
    }
}

