package com.blog.content.service;


import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyPage;


import java.util.Map;

public interface ArticleService {

    MyPage<ArticleVo> selectArticleListByPageAndUserId(ArticleVo articleVo) throws ValidException;
    ArticleVo selectArticleById(int articleId);
    int saveArticle(ArticleVo article) throws ValidException;
    int updateArticle(BlogUser blogUser, Article article) throws ValidException;
    Integer deleteArticle(BlogUser blogUser, String articleIds) throws ValidException;
}
