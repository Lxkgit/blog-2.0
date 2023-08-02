package com.blog.common.enums.file;


import java.util.List;

/**
 * @Author: lxk
 * @date 2023/5/11 20:34
 * @description: 文件存放路径枚举类
 */

public enum FilePathEnum {

    ARTICLE_PATH(1, "/article", "文章相关文件存放路径"),
    DOC_PATH(2, "/doc", "文档相关文件存放路径"),
    OTHER_PATH(3, "/other", "其它类型文件存放路径"),
    USER_PATH(4, "/user", "用户个人文件存放路径"),

    ;

    /**
     * 文件类型编码
     */
    private Integer filePathCode;

    /**
     * 文件类型名称
     */
    private String filePath;

    /**
     *
     */
    private String memo;

    public static String getFilePathByCode(Integer filePathCode) {
        for (FilePathEnum filePathEnum : FilePathEnum.values()) {
            if (filePathEnum.getFilePathCode().equals(filePathCode)) {
                return filePathEnum.getFilePath();
            }
        }
        return null;
    }

    FilePathEnum(Integer filePathCode, String filePath, String memo) {
        this.filePathCode = filePathCode;
        this.filePath = filePath;
        this.memo = memo;
    }

    public Integer getFilePathCode() {
        return filePathCode;
    }

    public void setFilePathCode(Integer filePathCode) {
        this.filePathCode = filePathCode;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
