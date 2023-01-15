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

    private Integer pageNum;

    private Integer pageSize;

    private List<DocCatalogVo> list;

    private boolean hasChildren;

    private Integer value;

    private boolean isLeaf;

    // 查询标志 0:只查询当前用户数据 1：查询全部数据
    private Integer type;

    @Override
    public int compareTo(DocCatalog o) {
        return this.getId() - o.getId();
    }
}
