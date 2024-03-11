package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2022/6/13 16:38
 * @description: 网站枚举类型
 */

public class Constant {

    public static final String articleSaveFail = "文章保存失败";
    public static final String articleUpdateFail = "文章修改失败";
    public static final String JWTError = "JWT解析报错";
    public static final String[] IMG_TYPE = {"jpg", "jpeg", "png"};

    // 数据状态:  0: 草稿 1: 发布  2: 置顶 3: 删除
    public static final Integer DRAFT = 0;
    public static final Integer RELEASE = 1;
    public static final Integer TOP = 2;
    public static final Integer DELETE = 3;

    // 文件目录信息  目录类型 0:本地目录 1:同步目录
    public static final Integer DIR_TYPE_LOCAL = 0;
    public static final Integer DIR_TYPE_SYNC = 1;

    // 文件/目录状态 0:不存在 1:存在（同步目录专用）
    public static final Integer DIR_STATUS_NOT_EXIST = 0;
    public static final Integer DIR_STATUS_EXIST = 1;

    // 文件类型
    public static final Integer FILE_TYPE_DIR = 0;
    public static final Integer FILE_TYPE_FILE = 1;
    public static final Integer FILE_TYPE_IMAGE = 2;

    // 设备离线时间
    public static final Long DEVICE_WAIT_TIME = 3*60L;

    // 设备离线：0  设备在线：1  设备删除：2
    public static final Integer DEVICE_OFFLINE = 0;
    public static final Integer DEVICE_ONLINE = 1;
    public static final Integer DEVICE_DELETE = 2;
    
    // netty 消息内部字段
    public static final String CHIP_TYPE = "chipType";
    public static final String SENSOR_TYPE = "sensorType";

    // netty 消息重发时间
    public static final Long NETTY_MSG_RETRY = 5*60L;
    // netty 消息重发次数
    public static final Integer NETTY_MSG_RETRY_COUNT = 5;
}