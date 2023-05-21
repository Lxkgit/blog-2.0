package com.blog.common.enums.file;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: lxk
 * @date 2023/5/11 20:34
 * @description: 文件类型枚举类
 */

public enum FileTypeEnum {

    IMAGE(1, "图片", Arrays.asList("jpg","png"), "/img"),
    FILE(2, "文件", Arrays.asList("jpg","png"), "/file"),

    ;

    /**
     * 文件类型编码
     */
    private Integer fileType;

    /**
     * 文件类型名称
     */
    private String fileTypeName;

    /**
     * 文件后缀名
     */
    private List<String> typeList;

    /**
     * 文件类型存放位置
     */
    private String fileTypePath;

    public static List<String> getTypeListByTypeName(Integer fileType) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.getFileType().equals(fileType)) {
                return fileTypeEnum.getTypeList();
            }
        }
        return null;
    }

    public static String getTypePathByTypeName(Integer fileType) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.getFileType().equals(fileType)) {
                return fileTypeEnum.getFileTypePath();
            }
        }
        return null;
    }

    FileTypeEnum(Integer fileType, String fileTypeName, List<String> typeList, String fileTypePath) {
        this.fileType = fileType;
        this.fileTypeName = fileTypeName;
        this.typeList = typeList;
        this.fileTypePath = fileTypePath;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    public List<String> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }

    public String getFileTypePath() {
        return fileTypePath;
    }

    public void setFileTypePath(String fileTypePath) {
        this.fileTypePath = fileTypePath;
    }
}
