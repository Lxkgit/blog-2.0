package com.blog.common.entity.content.doc.vo;

import com.blog.common.entity.content.doc.DocCatalog;
import lombok.Getter;
import lombok.Setter;

import java.text.Collator;
import java.util.List;
import java.util.Locale;

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

    private String selectType;

    @Override
    public int compareTo(DocCatalog o) {
        if (this.getSort().equals(o.getSort())) {
            Collator compare = Collator.getInstance(Locale.ENGLISH);
            return compare.compare(this.getDocName(), o.getDocName());
        } else {
            return this.getSort() - o.getSort();
        }
        //所有比较最底层的逻辑都是发生两两比较逻辑的,返回比较结果
        //只关心结果结果三种:
        //正数:   this.age - o.age    >
        //负数:   this.age - o.age    <
        //0       this   ==
        //return this.age-o.age; 升序排序
        //return o.age-this.age; 降序排序
    }
}
