package com.blog.common.entity.content;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: lxk
 * @date 2022/6/8 20:00
 * @description: 文章分类
 */

@Data
public class ArticleType {

    @TableId(value = "id")
    private Integer id;
    private int parentId;
    private String typeName;
    private int num;
}
