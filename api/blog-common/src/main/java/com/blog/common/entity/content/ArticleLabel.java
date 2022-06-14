package com.blog.common.entity.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: lxk
 * @date 2022/6/8 19:53
 * @description: 文章标签实体类
 */

@Data
public class ArticleLabel {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private int labelType;
    private String labelName;
    private int articleNum;
}
