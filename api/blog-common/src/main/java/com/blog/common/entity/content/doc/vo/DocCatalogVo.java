package com.blog.common.entity.content.doc.vo;

import com.blog.common.entity.content.doc.DocCatalog;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/20 10:38
 * @description:
 */

@Getter
@Setter
public class DocCatalogVo extends DocCatalog implements Comparable<DocCatalog>{

    private Integer pageSize;

    private Integer pageNum;

    private List<DocCatalogVo> list;

    private boolean hasChildren;

    @Override
    public int compareTo(DocCatalog o) {
        return this.getId() - o.getId();
    }
}
