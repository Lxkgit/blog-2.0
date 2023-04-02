package com.blog.content.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.ArticleLabelType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleLabelTypeDAO extends BaseMapper<ArticleLabelType> {

    List<ArticleLabelType> selectArticleLabelTypeList();
    int updateArticleLabelType(ArticleLabelType articleLabelType);
    void updateArticleLabelTypeLabelNumAdd(Integer id);
    void updateArticleLabelTypeLabelNumSubtract(Integer id);
    int deleteArticleLabelTypeByIds(@Param("ids") String[] ids);
}
