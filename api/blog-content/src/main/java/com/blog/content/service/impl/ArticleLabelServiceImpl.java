package com.blog.content.service.impl;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleLabelTypeDAO;
import com.blog.content.service.ArticleLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private ArticleLabelTypeDAO articleLabelTypeDAO;

    @Override
    public List<ArticleLabel> selectArticleLabelList(Integer labelType) {
        return articleLabelDAO.selectArticleLabelList(labelType);
    }

    @Override
    public void saveArticleLabel(ArticleLabel articleLabel) {
        articleLabel.setArticleNum(0);
        articleLabel.setCreateTime(new Date());
        articleLabel.setUpdateTime(new Date());
        articleLabelDAO.insert(articleLabel);
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumAdd(articleLabel.getLabelType());
    }

    @Override
    public int updateArticleLabel(ArticleLabel articleLabel) {
        ArticleLabel oldLabel = articleLabelDAO.selectById(articleLabel.getId());
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumSubtract(oldLabel.getLabelType());
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumAdd(articleLabel.getLabelType());
        return articleLabelDAO.updateArticleLabel(articleLabel);
    }

    @Override
    public Integer deleteArticleLabelByIds(String labelIds, Integer userId) {
        String[] ids = labelIds.split(",");
        for (String id : ids) {
            ArticleLabel articleLabel = articleLabelDAO.selectById(id);
            if (articleLabelDAO.deleteArticleLabelByIds(new String[]{id}, userId) == 1) {
                articleLabelTypeDAO.updateArticleLabelTypeLabelNumSubtract(articleLabel.getLabelType());
            }
        }
        return 0;
    }
}
