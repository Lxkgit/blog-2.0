package com.blog.content.service.impl;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.content.article.vo.ArticleLabelTypeVo;
import com.blog.common.entity.content.article.vo.ArticleLabelVo;
import com.blog.common.entity.user.BlogUser;
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

    @Override
    public List<ArticleLabelTypeVo> getArticleLabelTypeList() {
        Map<Integer, BlogUser> blogUserMap = new HashMap<>();
        List<ArticleLabelType> articleLabelTypeList;
        List<ArticleLabelTypeVo> articleLabelTypeVoList = new ArrayList<>();
        articleLabelTypeList = articleLabelTypeDAO.selectArticleLabelTypeList();
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

    @Override
    public int saveArticleLabelType(ArticleLabelType articleLabelType) {
        articleLabelType.setLabelNum(0);
        articleLabelType.setCreateTime(new Date());
        articleLabelType.setUpdateTime(new Date());
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
