package com.blog.common.entity.content.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blog.common.entity.content.doc.DocCatalog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.Collator;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: lxk
 * @date 2022/6/8 20:00
 * @description: 文章分类
 */

@Data
@TableName("article_type")
public class ArticleType implements Comparable<ArticleType> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer parentId;

    private String typeName;

    private Integer num;

    private Integer node;

    private Integer createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Override
    public int compareTo(ArticleType articleType) {
        return this.getId() - articleType.getId();
        //所有比较最底层的逻辑都是发生两两比较逻辑的,返回比较结果
        //只关心结果结果三种:
        //正数:   this.age - o.age    >
        //负数:   this.age - o.age    <
        //0       this   ==
        //return this.age-o.age; 升序排序
        //return o.age-this.age; 降序排序
    }
}
