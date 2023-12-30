package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.ArticleLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleLabelDAO extends BaseMapper<ArticleLabel> {

    /**
     * 查询文章标签总数
     * @return
     */
    Integer selectArticleLabelCount();
    List<ArticleLabel> selectArticleLabelList(@Param("labelType") Integer labelType);
    List<ArticleLabel> selectArticleLabelByArray(@Param("array") String[] labels);
    int updateArticleLabel(ArticleLabel articleLabel);
    int deleteArticleLabelByIds(@Param("ids") String[] ids, @Param("userId") Integer userId);
    void updateArticleLabelNumById(@Param("id") Integer id, @Param("articleNum") Integer articleNum);
}
