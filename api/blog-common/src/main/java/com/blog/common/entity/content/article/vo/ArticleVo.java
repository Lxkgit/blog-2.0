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
     * 接口查询位置 0：首页 1：管理页面
     */
    private Integer type;

    /**
     * 查询文章状态 0：草稿 1：已发布文章 2：置顶 3：已删除
     */
    private String selectStatus;

    /**
     * 查询文章所属用户 0：全部用户 x：指定用户
     */
    private Integer selectUser;

    /**
     * 排序方式 0：置顶排序 1：更新时间排序
     */
    private String sortType;

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

    public Integer getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(Integer  selectUser) {
        this.selectUser = selectUser;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
