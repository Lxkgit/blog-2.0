package com.blog.content.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.bo.ArticleBo;
import com.blog.common.entity.content.article.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface ArticleDAO extends BaseMapper<Article> {

    /**
     * 查询系统中全部文章（删除状态的除外）
     *
     * @return
     */
    Integer selectArticleCount();

    /**
     * 以用户id分组查询用户文章数（删除状态的除外）
     *
     * @return
     */
    List<Map<String, Integer>> selectArticleCountGroupByUserId();

    /**
     * 分页查询文章
     *
     * @param articleVo
     * @return
     */
    List<Article> selectArticleListByPage(ArticleVo articleVo);

    /**
     *  根据id与用户id修改文章
     * @param articleBo
     * @return
     */
    Integer updateArticle(ArticleBo articleBo);

    /**
     * 修改文章状态
     * @param articleBo
     * @return
     */
    Integer updateArticleStatus(ArticleBo articleBo);


}
