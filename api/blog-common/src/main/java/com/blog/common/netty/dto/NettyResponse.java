package com.blog.common.netty.dto;

import lombok.Data;

/**
 * @description: netty 消息响应类
 * @Author: lxk
 * @date 2024/1/11 14:13
 */

@Data
public class NettyResponse {

    /**
     * 消息唯一标识码
     */
    private String msgCode;

    /**
     * 消息处理结果
     */
    private boolean result;

    /**
     * 消息处理说明
     */
    private String message;
}
