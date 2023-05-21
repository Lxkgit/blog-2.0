package com.blog.common.entity.content.doc.enums;

/**
 * @Author: lxk
 * @date 2022/6/20 10:30
 * @description: 文档类型枚举类
 */

public enum DocType {

    CATALOG("catalog", 0 ),
    CONTENT("content", 1 ),
    ;


    DocType(String docType, int id) {
        this.docType = docType;
        this.id = id;
    }

    private String docType;
    private Integer id;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
