package com.blog.content.service;

import com.blog.common.entity.content.ArticleLabel;

import java.util.List;
import java.util.Map;

public interface ArticleLabelService {

    List<ArticleLabel> selectArticleLabelList(Integer userId);
    int saveArticleLabel(ArticleLabel articleLabel);
    int updateArticleLabel(ArticleLabel articleLabel);
    Map<String, Object> deleteArticleLabelByIds(String ids, Integer userId);
}
