package com.blog.content.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.bo.ArticleBo;
import com.blog.common.entity.content.article.vo.ArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO {

    Integer selectArticleCount(ArticleVo articleVo);
    List<Article> selectArticleListByPage(ArticleVo articleVo);
    Integer insertArticle(Article article);
    Article selectArticleById(@Param("id")int id);
    Integer updateArticle(ArticleBo articleBo);
    Integer deleteArticle(ArticleBo articleBo);

}
