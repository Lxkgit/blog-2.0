package com.blog.pi.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义Netty数据包类型
 */
@Getter
@AllArgsConstructor
public enum NettyPacketType {

    HEARTBEAT("心跳", "HEARTBEAT"),
    REQUEST("请求", "REQUEST"),
    RESPONSE("响应", "RESPONSE");

    private final String name;
    private final String value;
}
