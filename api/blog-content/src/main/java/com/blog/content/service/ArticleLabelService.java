package com.blog.content.service;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.vo.ArticleLabelVo;
import com.blog.common.exception.ValidException;

import java.util.List;
import java.util.Map;

public interface ArticleLabelService {

    Integer saveArticleLabel(ArticleLabelVo articleLabelVo) throws ValidException;
    Integer deleteArticleLabelByIds(String ids, Integer userId) throws ValidException;
    Integer updateArticleLabel(ArticleLabelVo articleLabelVo) throws ValidException;
    List<ArticleLabel> selectArticleLabelList(Integer userId);
}
