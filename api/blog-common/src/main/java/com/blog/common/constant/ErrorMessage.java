package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2023/1/9 20:34
 * @description:
 */

public enum ErrorMessage {

    /**
     * 01 网关服务
     */

    /**
     * 02 鉴权服务
     */

    /**
     * 03 用户服务
     */

    /**
     * 04 内容服务
     */

    /**
     * 05 文件服务
     */
    FILE_SIZE_NULL( "文件大小为空"),
    FILE_UPLOAD_ERROR( "文件上传失败"),
    FILE_TYPE_ERROR( "文件类型禁止上传"),
    FILE_TYPE_ERROR_SUFFIX("文件后缀类型禁止"),
    FILE_PATH_ERROR("文件存放路径编码错误"),

    ;



    private String desc;

    ErrorMessage(String desc) {

        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
