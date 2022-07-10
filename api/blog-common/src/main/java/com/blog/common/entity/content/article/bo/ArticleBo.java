package com.blog.common.entity.content.article.bo;

import com.blog.common.entity.content.article.Article;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lxk
 * @date 2022/6/13 17:53
 * @description:
 */

@Setter
@Getter
public class ArticleBo extends Article {
    private Integer updateUserId;
    private String[] ids;
}