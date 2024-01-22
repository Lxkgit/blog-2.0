package com.blog.content.service.impl;

import com.blog.common.constant.ErrorMessage;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import com.blog.common.entity.content.article.vo.ArticleLabelVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.exception.ValidException;
import com.blog.common.util.MyStringUtils;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleLabelTypeDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.service.ArticleLabelTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private UserClient userClient;

    /**
     * 新增标签分类
     *
     * @param articleLabelTypeVo
     * @return
     */
    @Override
    public Integer saveArticleLabelType(ArticleLabelTypeVo articleLabelTypeVo) {
        articleLabelTypeVo.setId(null);
        articleLabelTypeVo.setLabelNum(0);
        articleLabelTypeVo.setCreateTime(new Date());
        articleLabelTypeVo.setUpdateTime(new Date());
        articleLabelTypeDAO.insert(articleLabelTypeVo);
        return articleLabelTypeVo.getId();
    }

    /**
     * 删除标签分类
     *
     * @param articleLabelTypeIds
     * @return
     * @throws ValidException
     */
    @Override
    public Integer deleteArticleLabelTypeByIds(String articleLabelTypeIds) throws ValidException {
        Set<String> ids = MyStringUtils.splitString(articleLabelTypeIds, ",");
        for (String id : ids) {
            ArticleLabelType articleLabelType = articleLabelTypeDAO.selectById(Integer.parseInt(id));
            if (!articleLabelType.getLabelNum().equals(0)) {
                throw new ValidException(ErrorMessage.ARTICLE_LABEL_TYPE_NUMBER_ERROR);
            }
        }
        articleLabelTypeDAO.deleteArticleLabelTypeByIds(ids);
        return ids.size();
    }

    /**
     * 修改标签分类
     *
     * @param articleLabelTypeVo
     * @return
     */
    @Override
    public Integer updateArticleLabelType(ArticleLabelTypeVo articleLabelTypeVo) throws ValidException {
        ArticleLabelType articleLabelType = articleLabelTypeDAO.selectById(articleLabelTypeVo.getId());
        if (articleLabelType == null) {
            throw new ValidException(ErrorMessage.ARTICLE_LABEL_TYPE_NOT_EXISTS);
        }
        articleLabelTypeVo.setUpdateTime(new Date());
        articleLabelTypeDAO.updateArticleLabelType(articleLabelTypeVo);
        return articleLabelTypeVo.getId();
    }


    /**
     * 查询标签分类列表
     *
     * @return
     */
    @Override
    public List<ArticleLabelTypeVo> getArticleLabelTypeList() {
        Map<Integer, BlogUser> blogUserMap = new HashMap<>();
        List<ArticleLabelTypeVo> articleLabelTypeVoList = new ArrayList<>();
        List<ArticleLabelType> articleLabelTypeList = articleLabelTypeDAO.selectArticleLabelTypeList();
        for (ArticleLabelType articleLabelType : articleLabelTypeList) {
            ArticleLabelTypeVo articleLabelTypeVo = new ArticleLabelTypeVo();
            BlogUser labelTypeUser = blogUserMap.get(articleLabelType.getUserId());
            if (labelTypeUser == null) {
                blogUserMap.put(articleLabelType.getUserId(), userClient.selectUserById(articleLabelType.getUserId()));
            }

            List<ArticleLabel> articleLabelList = articleLabelDAO.selectArticleLabelList(articleLabelType.getId());
            List<ArticleLabelVo> articleLabelListVo = new ArrayList<>();
            for (ArticleLabel articleLabel : articleLabelList) {
                ArticleLabelVo articleLabelVo = new ArticleLabelVo();
                BeanUtils.copyProperties(articleLabel, articleLabelVo);
                articleLabelListVo.add(articleLabelVo);
            }
            articleLabelListVo.forEach(item -> {
                BlogUser labelUser = blogUserMap.get(item.getUserId());
                if (labelUser == null) {
                    blogUserMap.put(item.getUserId(), userClient.selectUserById(item.getUserId()));
                }
                item.setBlogUser(blogUserMap.get(item.getUserId()));
            });
            articleLabelTypeVo.setLabelList(articleLabelListVo);
            articleLabelTypeVo.setValue(articleLabelType.getId());
            articleLabelTypeVo.setLabel(articleLabelType.getTypeName());
            articleLabelTypeVo.setBlogUser(blogUserMap.get(articleLabelType.getUserId()));
            BeanUtils.copyProperties(articleLabelType, articleLabelTypeVo);
            articleLabelTypeVoList.add(articleLabelTypeVo);
        }
        return articleLabelTypeVoList;
    }


}
