package com.blog.common.exception;

import com.blog.common.constant.ErrorMessage;

public class ValidException extends BaseException {
    private static final long serialVersionUID = 1L;
    private String errMsg;

    public ValidException(String code) {
        super(code);
    }

    public ValidException(String code, String errMsg) {
        super(code, errMsg);
    }

    public ValidException(ErrorMessage errorMessage) {
        super(errorMessage.getCode(), errorMessage.getDesc());
        this.setErrorMessage(errorMessage);
    }

    public ValidException(ErrorMessage errorMessage, Object data) {
        super(errorMessage.getCode(), errorMessage.getDesc());
        this.setErrorMessage(errorMessage);
        this.setData(data);
    }

    public ValidException(String code, String errMsg, Throwable cause) {
        super(code, errMsg, cause);
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}