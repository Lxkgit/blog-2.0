package com.blog.content.service.impl;

import com.blog.common.entity.content.ArticleLabel;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.service.ArticleLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: lxk
 * @date: 2022/6/15 18:56
 * @description: 文章标签服务类
 * @modified By:
 */

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {

    @Resource
    private ArticleLabelDAO articleLabelDAO;

    @Override
    public List<ArticleLabel> selectArticleLabelList(Integer userId) {
        return articleLabelDAO.selectArticleLabel(userId);
    }

    @Override
    public int saveArticleLabel(ArticleLabel articleLabel) {
        return articleLabelDAO.insert(articleLabel);
    }

    @Override
    public int updateArticleLabel(ArticleLabel articleLabel) {
        return articleLabelDAO.updateArticleLabel(articleLabel);
    }

    @Override
    public Map<String, Object> deleteArticleLabelByIds(String labelIds, Integer userId) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = labelIds.split(",");
        int success = articleLabelDAO.deleteArticleLabelByIds(ids, userId);
        map.put("delete", ids.length);
        map.put("success", success);
        if (ids.length != success) {
            map.put("msg", "请确认要删除的标签是否被其它文章使用.. ");
        }
        return map;
    }
}
