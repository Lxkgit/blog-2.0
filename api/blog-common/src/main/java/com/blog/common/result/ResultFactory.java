package com.blog.common.result;

/**
 * @author: lxk
 * @date: 2021/6/30 10:29
 * @description: 构建返回信息
 * @modified By:
 */
public class ResultFactory {

    public static Result buildSuccessResult(Object data) {
        return buildResult(ResultCode.SUCCESS.code, "成功", data, true);
    }

    public static Result buildSuccessResult() {
        return buildResult(ResultCode.SUCCESS.code, "成功", null, true);
    }

    public static Result buildFailResult(String message) {
        return buildResult(ResultCode.FAIL.code, message, null, false);
    }

    public static Result buildFailResult(String errorCode, String message) {
        return buildResult(errorCode, message, null, false);
    }

    public static Result buildFailResult(String errorCode, String message, Object data) {
        return buildResult(errorCode, message, data, false);
    }

    public static Result buildResult(String resultCode, String message, Object data, boolean success) {
        return new Result(resultCode, message, data, success);
    }
}
