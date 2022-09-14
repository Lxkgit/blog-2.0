package com.blog.content.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.bo.ArticleBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO extends BaseMapper<Article> {

    Integer selectArticleCount(@Param("userId") int userId);
    List<Article> selectArticleListByPage(@Param("startNum")int startNum, @Param("pageSize") int pageSize, @Param("userId") int userId);
    Article selectArticleById(@Param("id")int id);
    Integer updateArticle(ArticleBo articleBo);
    Integer deleteArticle(ArticleBo articleBo);

}
