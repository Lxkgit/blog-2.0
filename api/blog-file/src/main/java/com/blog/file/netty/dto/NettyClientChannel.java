package com.blog.file.netty.dto;

import io.netty.channel.ChannelId;
import lombok.Data;

import java.util.Date;

/**
 * @description: netty客户端通道信息
 * @Author: lxk
 * @date 2024/1/29 19:32
 */

@Data
public class NettyClientChannel {

    /**
     * netty通道
     */
    private ChannelId channelId;

    /**
     * netty注册id
     */
    private String registerId;

    /**
     * netty所属用户id
     */
    private Integer userId;

    /**
     * netty传输消息数据
     */
    private Date date;

    public NettyClientChannel(ChannelId channelId, String registerId, Integer userId, Date date) {
        this.channelId = channelId;
        this.registerId = registerId;
        this.userId = userId;
        this.date = date;
    }
}
