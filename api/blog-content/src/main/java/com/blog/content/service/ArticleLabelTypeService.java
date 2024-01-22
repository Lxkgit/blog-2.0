package com.blog.content.service;

import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import com.blog.common.exception.ValidException;

import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/19 21:32
 * @description:
 * @modified By:
 */
public interface ArticleLabelTypeService {

    Integer saveArticleLabelType(ArticleLabelTypeVo articleLabelTypeVo);
    Integer deleteArticleLabelTypeByIds(String ids) throws ValidException;
    Integer updateArticleLabelType(ArticleLabelTypeVo articleLabelTypeVo) throws ValidException;
    List<ArticleLabelTypeVo> getArticleLabelTypeList();
}
