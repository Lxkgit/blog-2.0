package com.blog.common.constant;

/**
 * @Author: lxk
 * @date 2022/6/13 16:38
 * @description: 网站报错类型
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
}