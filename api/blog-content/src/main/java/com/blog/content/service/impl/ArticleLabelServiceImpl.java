package com.blog.content.service.impl;

import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.vo.ArticleLabelVo;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyStringUtils;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleLabelTypeDAO;
import com.blog.content.mq.send.SendSystemData;
import com.blog.content.service.ArticleLabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Resource
    private SendSystemData sendSystemData;

    /**
     * 新增文章标签
     *
     * @param articleLabelVo
     * @throws ValidException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveArticleLabel(ArticleLabelVo articleLabelVo) throws ValidException {
        articleLabelVo.setId(null);
        articleLabelVo.setArticleNum(0);
        articleLabelVo.setCreateTime(new Date());
        articleLabelVo.setUpdateTime(new Date());
        if (articleLabelTypeDAO.selectById(articleLabelVo.getLabelType()) == null) {
            throw new ValidException(ErrorMessage.ARTICLE_LABEL_TYPE_NOT_EXISTS);
        }
        articleLabelDAO.insert(articleLabelVo);
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumAdd(articleLabelVo.getLabelType());
        // 发送博客系统新增文章标签mq消息
        sendSystemData.sendSystemData(SendSystemData.articleLabel, 1);
        return articleLabelVo.getId();
    }

    /**
     * 删除文章标签
     *
     * @param labelIds
     * @param userId
     * @return
     * @throws ValidException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteArticleLabelByIds(String labelIds, Integer userId) throws ValidException {
        Set<String> idSet = MyStringUtils.splitString(labelIds, ",");

        List<ArticleLabel> articleLabelList = articleLabelDAO.selectBatchIds(idSet);
        for (ArticleLabel articleLabel : articleLabelList) {
            if (articleLabel.getArticleNum() != 0) {
                String errorMag = "文章标签【" + articleLabel.getLabelName() + "】下文章数量不为0";
                throw new ValidException(ErrorMessage.ARTICLE_LABEL_NUM_ERROR, errorMag);
            }
            if (!articleLabel.getUserId().equals(userId)) {
                String errorMag = "文章标签【" + articleLabel.getLabelName() + "】创建者不为你";
                throw new ValidException(ErrorMessage.ARTICLE_LABEL_USER_DELETE_ERROR, errorMag);
            }
        }

        articleLabelDAO.deleteArticleLabelByIds(idSet, userId);
        for (ArticleLabel articleLabel : articleLabelList) {
            articleLabelTypeDAO.updateArticleLabelTypeLabelNumSubtract(articleLabel.getLabelType());
        }

        // 发送博客系统删除文章标签mq消息
        sendSystemData.sendSystemData(SendSystemData.articleLabel, -idSet.size());
        return idSet.size();
    }

    /**
     * 修改文章标签
     *
     * @param articleLabelVo
     * @return
     * @throws ValidException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateArticleLabel(ArticleLabelVo articleLabelVo) throws ValidException {
        ArticleLabel oldLabel = articleLabelDAO.selectById(articleLabelVo.getId());
        if (oldLabel == null) {
            throw new ValidException(ErrorMessage.ARTICLE_LABEL_NOT_EXISTS);
        }
        if (!oldLabel.getUserId().equals(articleLabelVo.getUserId())) {
            throw new ValidException(ErrorMessage.ARTICLE_LABEL_USER_UPDATE_ERROR);
        }
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumSubtract(oldLabel.getLabelType());
        articleLabelTypeDAO.updateArticleLabelTypeLabelNumAdd(articleLabelVo.getLabelType());
        articleLabelDAO.updateArticleLabel(articleLabelVo);
        return articleLabelVo.getId();
    }

    /**
     * 根据标签分组id查询标签
     *
     * @param labelType
     * @return
     */
    @Override
    public List<ArticleLabel> selectArticleLabelList(Integer labelType) {
        return articleLabelDAO.selectArticleLabelList(labelType);
    }

}
