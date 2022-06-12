package com.blog.common.result;

import lombok.Data;

/**
 * @author: lxk
 * @date: 2021/6/30 10:27
 * @description: 返回信息
 * @modified By:
 */
@Data
public class Result {
    private int code;
    private String message;
    private Object result;

    public Result() {
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.result = data;
    }
}
