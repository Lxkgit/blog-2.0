package com.blog.common.result;

/**
 * @author: lxk
 * @date: 2021/6/30 10:29
 * @description: 构建返回信息
 * @modified By:
 */
public class ResultFactory {
    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS, "成功", data);
    }

    public static Result buildSuccessResult() {
        return buildResult(ResultCode.SUCCESS, "成功", null);
    }

    public static Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object data) {
        return buildResult(resultCode.code, message, data);
    }

    public static Result buildResult(int resultCode, String message, Object data) {
        return new Result(resultCode, message, data);
    }
}
