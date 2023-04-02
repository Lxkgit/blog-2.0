package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.user.BlogUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/14 16:35
 * @description: 前端文章类型实体
 */

@Getter
@Setter
public class ArticleTypeVo extends ArticleType {

    private String value;

    private String label;

    private BlogUser blogUser;

    private List<ArticleTypeVo> children;
}