package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.ArticleLabel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleLabelDAO extends BaseMapper<ArticleLabel> {

    List<ArticleLabel> selectArticleLabelByArray(@Param("array") String[] labels);
}
