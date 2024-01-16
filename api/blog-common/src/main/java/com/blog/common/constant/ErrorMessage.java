package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2023/1/9 20:34
 * @description:
 */

public enum ErrorMessage {

    /**
     * 00 全局异常
     */
    // 参数校验： 00
    PARAMETER_VERIFICATION_ERROR( "000001","参数校验错误"),
    // 业务流程： 01
    UNKNOWN_ERROR( "000102","未知错误"),

    /**
     * 01 网关服务
     */

    /**
     * 02 鉴权服务
     */

    /**
     * 03 用户服务
     */
    USER_VERIFICATION_CODE_ALREADY_EXISTS("03001","用户验证码已存在,请检查邮箱信件"),
    USER_VERIFICATION_CODE_SEND_SUCCESS("03002","用户验证码发送成功"),
    /**
     * 04 内容服务
     */

    /**
     * 05 文件服务
     */

    // 文件上传：00
    FILE_SIZE_NULL( "050001","文件大小为空"),
    FILE_UPLOAD_ERROR( "050002","文件上传失败"),
    FILE_TYPE_ERROR( "050003","文件类型禁止上传"),
    FILE_TYPE_ERROR_SUFFIX("050004","文件后缀类型禁止"),
    FILE_PATH_ERROR("050005","文件存放路径编码错误"),

    FILE_NAME_NULL_ERROR("050006","目录名称为空"),
    FILE_NAME_SAME_ERROR("050007","目录名称已存在"),
    FILE_NAME_NULL("050008","文件名称为空"),


    BASE_FILE_DIR_NOT_CREATE("050008","根目录下禁止创建目录"),
    BASE_FILE_DIR_NOT_DELETE("050009","基础文件目录无法删除"),
    BASE_FILE_NOT_RENAME("050010","基础文件目录禁止重命名"),
    ;



    private String code;
    private String desc;

    ErrorMessage(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
