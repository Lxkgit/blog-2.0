package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.ArticleLabel;
import com.blog.common.entity.content.ArticleLabelType;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleLabelTypeDAO extends BaseMapper<ArticleLabelType> {
}
