package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.ArticleType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleTypeDAO extends BaseMapper<ArticleType> {

    List<ArticleType> selectArticleTypeByArray(@Param("array") String[] types);
    int updateArticleType(ArticleType articleType);
    int deleteArticleTypeByIds(@Param("ids") String[] ids);
}
