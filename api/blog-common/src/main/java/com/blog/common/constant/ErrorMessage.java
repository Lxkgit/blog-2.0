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
    // 文章：00
    ARTICLE_NULL("040001", "文章不存在"),
    // 文章分类：01
    ARTICLE_TYPE_ERROR("040101", "文章分类不存在"),
    ARTICLE_TYPE_PARENT_ERROR("040102", "文章分类父级不存在"),
    ARTICLE_TYPE_LEVEL_ERROR("040103", "文章分类只支持三级"),
    ARTICLE_TYPE_NUM_ERROR("040104", "文章分类下文章数量不为0"),
    // 文章标签与标签分类：02
    ARTICLE_LABEL_NOT_EXISTS("040201", "文章标签不存在"),
    ARTICLE_LABEL_TYPE_NOT_EXISTS("040202", "文章标签分类不存在"),
    ARTICLE_LABEL_NUM_ERROR("040203", "文章标签下文章数量不为0"),
    ARTICLE_LABEL_USER_UPDATE_ERROR("040204", "只允许修改自己创建的标签"),
    ARTICLE_LABEL_USER_DELETE_ERROR("040205", "只允许删除自己创建的标签"),
    ARTICLE_LABEL_TYPE_NUMBER_ERROR("040206", "文章标签分类下标签数量不为0"),
    // 日记：03
    DIARY_NOT_EXISTS("040301", "日记不存在"),
    /**
     * 05 文件服务
     */

    // 文件上传：00
    FILE_TYPE_ERROR_SUFFIX("050004","文件后缀类型禁止"),
    FILE_NAME_NULL_ERROR("050006","目录名称为空"),
    FILE_NAME_SAME_ERROR("050007","目录名称已存在"),
    FILE_NAME_NULL("050008","文件名称为空"),


    BASE_FILE_DIR_NOT_CREATE("050008","根目录下禁止创建目录"),
    BASE_FILE_DIR_NOT_DELETE("050009","基础文件目录无法删除"),
    BASE_FILE_NOT_RENAME("050010","基础文件目录禁止重命名"),

    // 远程设备：01
    DEVICE_CODE_EXISTS("050101", "设备编码已存在"),
    DEVICE_NOT_EXISTS("050102", "设备不存在"),

    // 单片机：02
    CHIP_NOT_EXISTS("050201", "单片机不存在"),
    CHIP_CODE_EXISTS("050202", "单片机编码已存在"),

    // 传感器：03
    SENSOR_NOT_EXISTS("050301", "传感器不存在"),
    SENSOR_CONTROL_NOT_EXISTS("050302", "传感器控制指令不存在"),
    SENSOR_USER_ERROR("050303", "只允许修改自己的传感器"),
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
