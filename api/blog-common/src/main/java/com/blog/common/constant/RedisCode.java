package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2023/1/9 20:34
 * @description:
 */

public enum RedisCode {

    /**
     * 01 网关服务
     */

    /**
     * 02 鉴权服务
     */

    /**
     * 03 用户服务
     */
    USER_VERIFICATION_CODE("verify_code:", "redis验证码前缀")
    /**
     * 04 内容服务
     */

    /**
     * 05 文件服务
     */


    ;



    private String key;
    private String desc;

    RedisCode(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
