package com.blog.content.service;


import com.blog.common.entity.content.Article;
import com.blog.common.entity.content.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;


import java.util.Map;

public interface ArticleService {

    MyPage<ArticleVo> selectArticleListByPage(int page, int size);
    MyPage<ArticleVo> selectArticleListByPageAndUserId(int page, int size, int userId);
    int saveArticle(Article article);
    int updateArticle(BlogUser blogUser, Article article);
    Map<String, Integer> deleteArticle(BlogUser blogUser, String article);
}
