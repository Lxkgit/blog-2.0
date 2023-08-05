package com.blog.common.exception;

import com.blog.common.constant.ErrorMessage;

public abstract class BaseException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errMsg;
    private String code;
    private ErrorMessage errorMessage;
    private Object args;
    private Object data;

    public BaseException(String code) {
        this.code = code;
    }

    public BaseException(String code, String errMsg) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

    public BaseException(String code, String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.code = code;
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getArgs() {
        return args;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}