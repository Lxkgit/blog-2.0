package com.blog.file.netty.dto;

import lombok.Data;

/**
 * @description: netty 消息响应类
 * @Author: 308501
 * @date 2024/1/11 14:13
 */

@Data
public class NettyResponse {

    // 消息处理结果
    private boolean result;

    // 消息处理说明
    private String message;

    public NettyResponse(boolean result) {
        this.result = result;
    }

    public NettyResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
