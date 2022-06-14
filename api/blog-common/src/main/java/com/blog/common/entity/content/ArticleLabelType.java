package com.blog.common.entity.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: lxk
 * @date 2022/6/8 19:55
 * @description: 文章标签分类实体类
 */

@Data
public class ArticleLabelType {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String typeName;

    private int labelNum;
}
