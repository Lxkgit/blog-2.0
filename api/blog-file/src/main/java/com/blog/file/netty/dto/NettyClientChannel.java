package com.blog.file.netty.dto;

import io.netty.channel.ChannelId;
import lombok.Data;

import java.util.Date;

/**
 * @description: netty客户端通道信息
 * @Author: 308501
 * @date 2024/1/29 19:32
 */

@Data
public class NettyClientChannel {

    private ChannelId channelId;

    private String registerId;

    private String username;

    private Date date;

    public NettyClientChannel(ChannelId channelId, String registerId, String username, Date date) {
        this.channelId = channelId;
        this.registerId = registerId;
        this.username = username;
        this.date = date;
    }
}
