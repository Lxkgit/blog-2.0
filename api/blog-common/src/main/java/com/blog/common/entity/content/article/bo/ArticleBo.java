package com.blog.common.entity.content.article.bo;

import com.blog.common.entity.content.article.Article;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

/**
 * @Author: lxk
 * @date 2022/6/13 17:53
 * @description: bo 对数据进行检索和处理的, 业务对象通常位于中间层或者业务逻辑层。
 */

@Setter
@Getter
public class ArticleBo extends Article {
    private Integer updateUserId;
    private Set<String> ids;
}