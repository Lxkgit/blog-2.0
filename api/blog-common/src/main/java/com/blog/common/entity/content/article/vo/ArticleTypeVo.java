package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleType;
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

    private List<ArticleType> list;
}