package com.blog.content.service;

import com.blog.common.entity.content.ArticleLabelType;
import com.blog.common.entity.content.vo.ArticleLabelTypeVo;

import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/19 21:32
 * @description:
 * @modified By:
 */
public interface ArticleLabelTypeService {

    List<ArticleLabelTypeVo> getArticleLabelTypeList(String type, Integer id);
    int saveArticleLabelType(ArticleLabelType articleLabelType);
    int updateArticleLabelType(ArticleLabelType articleLabelType);
    Map<String, Object> deleteArticleLabelTypeByIds(String ids);

}
