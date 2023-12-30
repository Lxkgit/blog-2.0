package com.blog.common.enums.mq;

public enum RocketMQMsgEnum {

    ALL(0, "MQ消息全量更新"),
    ADD(1, "MQ消息增量更新"),

    ;

    /**
     *  mq消息类型 0：全量 1：增量
     */
    private Integer type;

    /**
     * 备注
     */
    private String memo;

    RocketMQMsgEnum(Integer type, String memo) {
        this.type = type;
        this.memo = memo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
