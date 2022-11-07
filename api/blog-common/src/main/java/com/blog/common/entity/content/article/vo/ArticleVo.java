package com.blog.common.entity.content.article.vo;


import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.user.BlogUser;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/9 9:38
 * @description: 前端文章实体
 */

public class ArticleVo extends Article {

    private Integer pageSize;

    private Integer pageNum;

    private BlogUser blogUser;
    
    private List<ArticleType> articleTypes;
    
    private List<ArticleLabel> articleLabels;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public BlogUser getBlogUser() {
        return blogUser;
    }

    public void setBlogUser(BlogUser blogUser) {
        this.blogUser = blogUser;
    }

    public List<ArticleType> getArticleTypes() {
        return articleTypes;
    }

    public void setArticleTypes(List<ArticleType> articleTypes) {
        this.articleTypes = articleTypes;
    }

    public List<ArticleLabel> getArticleLabels() {
        return articleLabels;
    }

    public void setArticleLabels(List<ArticleLabel> articleLabels) {
        this.articleLabels = articleLabels;
    }
}
