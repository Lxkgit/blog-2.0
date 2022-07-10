package com.blog.content.service.impl;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleLabelTypeDAO;
import com.blog.content.service.ArticleLabelTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/19 21:32
 * @description:
 * @modified By:
 */
@Service
public class ArticleLabelTypeServiceImpl implements ArticleLabelTypeService {

    @Resource
    private ArticleLabelDAO articleLabelDAO;

    @Resource
    private ArticleLabelTypeDAO articleLabelTypeDAO;

    @Override
    public List<ArticleLabelTypeVo> getArticleLabelTypeList(String type, Integer id) {
        List<ArticleLabelTypeVo> articleLabelTypeVoList;
        articleLabelTypeVoList = articleLabelTypeDAO.selectArticleLabelTypeList(id);
        if (articleLabelTypeVoList != null && articleLabelTypeVoList.size() > 0 && type != null && type.equals("detail")){
            for (ArticleLabelTypeVo labelTypeVo : articleLabelTypeVoList){
                List<ArticleLabel> articleLabelList = articleLabelDAO.selectArticleLabelByLabelType(labelTypeVo.getId());
                labelTypeVo.setLabelList(articleLabelList);
            }
        }
        return articleLabelTypeVoList;
    }

    @Override
    public int saveArticleLabelType(ArticleLabelType articleLabelType) {
        return articleLabelTypeDAO.insert(articleLabelType);
    }

    @Override
    public int updateArticleLabelType(ArticleLabelType articleLabelType) {
        return articleLabelTypeDAO.updateArticleLabelType(articleLabelType);
    }

    @Override
    public Map<String, Object> deleteArticleLabelTypeByIds(String articleLabelTypeIds) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = articleLabelTypeIds.split(",");
        int num = articleLabelTypeDAO.deleteArticleLabelTypeByIds(ids);
        map.put("delete", ids.length);
        map.put("success", num);
        if (ids.length != num) {
            map.put("msg", "请确认所选标签分类下标签数是否为0");
        }
        return map;
    }
}
