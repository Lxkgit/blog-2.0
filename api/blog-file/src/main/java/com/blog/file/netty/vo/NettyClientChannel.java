package com.blog.file.netty.vo;

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
     * netty所属用户
     */
    private String username;

    /**
     * netty传输消息数据
     */
    private Date date;

    public NettyClientChannel(ChannelId channelId, String registerId, String username, Date date) {
        this.channelId = channelId;
        this.registerId = registerId;
        this.username = username;
        this.date = date;
    }
}
