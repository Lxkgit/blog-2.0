package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleLabelTypeDAO extends BaseMapper<ArticleLabelType> {

    List<ArticleLabelTypeVo> selectArticleLabelTypeList(@Param("id") Integer id);
    int updateArticleLabelType(ArticleLabelType articleLabelType);
    int deleteArticleLabelTypeByIds(@Param("ids") String[] ids);
}
