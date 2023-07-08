package com.blog.common.entity.file.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.file.ContentCount;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @description: 博客用户内容数量统计表
 * @Author: 308501
 * @date 2023/6/28 17:23
 */

@Getter
@Setter
public class ContentCountVo extends ContentCount implements Comparable<ContentCountVo>{

    @Override
    public int compareTo(ContentCountVo countVo) {
        return this.getUserId() - countVo.getUserId();
        //所有比较最底层的逻辑都是发生两两比较逻辑的,返回比较结果
        //只关心结果结果三种:
        //正数:   this.age - o.age    >
        //负数:   this.age - o.age    <
        //0       this   ==
        //return this.age-o.age; 升序排序
        //return o.age-this.age; 降序排序
    }
}
