package com.blog.content.service;

import com.blog.common.entity.content.article.ArticleLabel;

import java.util.List;
import java.util.Map;

public interface ArticleLabelService {

    List<ArticleLabel> selectArticleLabelList(Integer userId);
    void saveArticleLabel(ArticleLabel articleLabel);
    int updateArticleLabel(ArticleLabel articleLabel);
    Integer deleteArticleLabelByIds(String ids, Integer userId);
}
