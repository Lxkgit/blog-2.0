package com.blog.common.result;

/**
 * @author: lxk
 * @date: 2021/6/30 10:25
 * @description: 返回码
 * @modified By:
 */
public enum ResultCode {
    SUCCESS("200"),
    FAIL("400"),
    UNAUTHORIZED("401"),
    NOT_FOUND("404"),
    INTERNAL_SERVER_ERROR("500");

    public String code;

    ResultCode(String code) {
        this.code = code;
    }
}
