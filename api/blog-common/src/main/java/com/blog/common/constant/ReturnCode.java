package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2023/1/9 20:34
 * @description:
 */

public enum ReturnCode {

    VIDEO_COMPRESS_DEVICE_NOT_EXIST(20990016, "The video compression device does not exist");


    private int code;

    private String desc;

    ReturnCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
