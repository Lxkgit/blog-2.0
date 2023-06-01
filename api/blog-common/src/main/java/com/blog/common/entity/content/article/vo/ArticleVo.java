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

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 接口访问用户
     */
    private BlogUser blogUser;

    /**
     *
     */
    private String type;

    /**
     * 查询文章状态 0：全部文章 1：已发布文章 2：草稿类文章
     */
    private String selectStatus;

    /**
     * 查询文章所属用户 0：全部用户 x：指定用户
     */
    private String selectUser;

    /**
     *
     */
    private List<String> articleLabelList;

    private List<String> articleTypeList;

    /**
     * 文章分类
     */
    private List<ArticleType> articleTypes;

    /**
     * 文章标签
     */
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

    public List<String> getArticleTypeList() {
        return articleTypeList;
    }

    public void setArticleTypeList(List<String> articleTypeList) {
        this.articleTypeList = articleTypeList;
    }

    public List<String> getArticleLabelList() {
        return articleLabelList;
    }

    public void setArticleLabelList(List<String> articleLabelList) {
        this.articleLabelList = articleLabelList;
    }

    public String getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(String selectStatus) {
        this.selectStatus = selectStatus;
    }

    public String getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(String selectUser) {
        this.selectUser = selectUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
