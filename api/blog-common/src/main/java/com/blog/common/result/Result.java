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
    private String code;
    private String message;
    private Object result;
    private boolean success;

    public Result() {
    }

    public Result(String code, String message, Object data, boolean success) {
        this.code = code;
        this.message = message;
        this.result = data;
        this.success = success;
    }
}
