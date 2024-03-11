package com.blog.file.netty.dto;

import io.netty.channel.ChannelId;
import lombok.Data;

import java.util.Date;

/**
 * @description: Netty消息重发类
 * @Author: 308501
 * @date 2024/3/11 15:45
 */

@Data
public class NettyMessageRetry {

    /**
     * 消息接收通道id
     */
    private ChannelId channelId;

    /**
     * netty 消息
     */
    private String msg;

    /**
     * 消息发送时间
     */
    private Date date;

    /**
     * 重发次数
     */
    private Integer retryCount;


    public NettyMessageRetry(ChannelId channelId, String msg, Date date, Integer retryCount) {
        this.channelId = channelId;
        this.msg = msg;
        this.date = date;
        this.retryCount = retryCount;
    }
}
