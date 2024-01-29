package com.blog.pi.netty.dto;


import com.blog.pi.config.InitConfig;
import com.blog.pi.netty.enums.NettyPacketType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Andon
 * 2022/7/27
 * <p>
 * 自定义Netty数据包
 */
@Data
@Slf4j
public class NettyPacket<T> implements Serializable {

//    private static final long serialVersionUID = -7355782421412073639L;

    public static Map<String, NettyPacket<Object>> MESSAGE_QUEUE = new ConcurrentHashMap<>();

    // netty 消息唯一序列号
    private String requestId;
    // netty 请求类型
    private String nettyPacketType;
    // netty 消息Topic
    private String topic;
    // netty注册id
    private String registerId;
    // 消息所属用户
    private String username;
    // netty 消息内容
    private T data;

    public static <T> NettyPacket<T> buildRequest(T param) {
        NettyPacket<T> nettyPacket = new NettyPacket<>();
        nettyPacket.setRequestId(getOnlyId());
        nettyPacket.setNettyPacketType(NettyPacketType.REQUEST.getValue());
        nettyPacket.setRegisterId((String) InitConfig.getRegisterConfig("netty", "registerId"));
        nettyPacket.setUsername((String) InitConfig.getRegisterConfig("netty", "username"));
        nettyPacket.setData(param);
        return nettyPacket;
    }

    public static <T> NettyPacket<T> buildResponse(String requestId, T data) {
        NettyPacket<T> nettyPacket = new NettyPacket<>();
        nettyPacket.setRequestId(requestId);
        nettyPacket.setNettyPacketType(NettyPacketType.RESPONSE.getValue());
        nettyPacket.setRegisterId((String) InitConfig.getRegisterConfig("netty", "registerId"));
        nettyPacket.setData(data);
        return nettyPacket;
    }

    public static void request(String requestId, NettyPacket<Object> nettyResponse) {
        MESSAGE_QUEUE.put(requestId, nettyResponse);
    }

    public static void response(String requestId) {
        MESSAGE_QUEUE.remove(requestId);
    }

    private static String getOnlyId() {
        return UUID.randomUUID().toString();
    }
}
