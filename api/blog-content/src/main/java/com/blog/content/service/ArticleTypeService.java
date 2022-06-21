package com.blog.content.service;

import com.blog.common.entity.content.ArticleType;
import com.blog.common.entity.content.vo.ArticleTypeVo;

import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/14 22:02
 * @description:
 * @modified By:
 */
public interface ArticleTypeService {

    List<ArticleType> selectArticleTypeList();
    List<ArticleTypeVo> selectArticleTypeTree();
    int saveArticleType(ArticleType articleType);
    int updateArticleType(ArticleType articleType);
    Map<String, Object> deleteArticleTypeById(String articleTypeId);
}
