package com.blog.content.service;

import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.article.vo.ArticleTypeVo;
import com.blog.common.exception.ValidException;

import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/14 22:02
 * @description:
 * @modified By:
 */
public interface ArticleTypeService {

    /**
     * 创建文章分类
     *
     * @param articleType
     * @return
     */
    Integer saveArticleType(ArticleTypeVo articleType);

    /**
     * 删除文章分类
     *
     * @param articleTypeId
     * @return
     */
    Integer deleteArticleTypeById(String articleTypeId) throws ValidException;

    /**
     * 修改文章分类
     *
     * @param articleType
     * @return
     */
    Integer updateArticleType(ArticleTypeVo articleType) throws ValidException;

    /**
     * 根据传入的父节点id获取子节点
     *
     * @return
     */
    List<ArticleType> selectArticleTypeByParentId(Integer parentId);

    /**
     * 通过id查询文章分类
     * @param id
     * @return
     */
    List<ArticleType> selectArticleTypeById(Integer id);

    /**
     * 查询全部文章分类
     * @return
     */
    List<ArticleType> selectArticleTypeList();

    /**
     * 查询文章分类，并返回树结构组织
     * @return
     */
    List<ArticleTypeVo> selectArticleTypeTree();
}
