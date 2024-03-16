package com.blog.pi.netty.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 心跳消息类
 * @Author: 308501
 * @date 2024/3/14 15:12
 */

@Data
public class NettyHeartBeat {

    /**
     * 心跳时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date heartBeat;

    /**
     * 消息来源
     */
    private String from;

    /**
     * 心跳类型
     * 1： 树莓派等服务器设备
     */
    private Integer type;
}
