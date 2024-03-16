package com.blog.file.netty.enums;

public enum HeartBeatType {

    // 服务器设备
    SERVICE(1),
    ;

    /**
     * topic
     */
    private Integer type;

    HeartBeatType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
