package com.blog.pi.netty.event;


import com.blog.pi.netty.dto.NettyPacket;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @description:
 * @Author: lxk
 * @date 2024/1/6 15:15
 */
@Getter
public class NettyPacketEvent extends ApplicationEvent {

    private NettyPacket<Object> nettyPacket;

    public NettyPacketEvent(Object source, NettyPacket<Object> nettyPacket) {
        super(source);
        this.nettyPacket = nettyPacket;
    }
}

