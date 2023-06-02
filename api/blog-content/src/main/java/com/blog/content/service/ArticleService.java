package com.blog.content.service;


import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;


import java.util.Map;

public interface ArticleService {

    MyPage<ArticleVo> selectArticleListByPageAndUserId(ArticleVo articleVo);
    ArticleVo selectArticleById(int articleId);
    int saveArticle(Article article);
    int updateArticle(BlogUser blogUser, Article article);
    Map<String, Integer> deleteArticle(BlogUser blogUser, String article);
}
