package com.blog.content.service;


import com.blog.common.entity.content.Article;
import com.blog.common.entity.content.vo.ArticleVo;
import com.blog.common.util.MyPage;

import java.security.Principal;
import java.util.List;

public interface ArticleService {

    MyPage<ArticleVo> selectArticleListByPage(int page, int size);
    MyPage<ArticleVo> selectArticleListByPageAndUserId(Principal principal, int page, int size);
    List<Article> selectArticleListAll();

}
