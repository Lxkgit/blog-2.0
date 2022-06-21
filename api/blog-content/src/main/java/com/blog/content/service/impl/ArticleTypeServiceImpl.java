package com.blog.content.service.impl;

import com.blog.common.entity.content.ArticleType;
import com.blog.common.entity.content.vo.ArticleTypeVo;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.service.ArticleTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lxk
 * @date 2022/6/14 16:31
 * @description: 文章类型服务类
 */

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Resource
    private ArticleTypeDAO articleTypeDAO;

    @Override
    public List<ArticleType> selectArticleTypeList() {
        return articleTypeDAO.selectList(null);
    }

    @Override
    public List<ArticleTypeVo> selectArticleTypeTree() {
        List<ArticleType> articleTypeList = articleTypeDAO.selectList(null);
        List<ArticleTypeVo> articleTypeVoList = new ArrayList<>();
        BeanUtils.copyProperties(articleTypeList, articleTypeVoList);

        // Todo
        return articleTypeVoList;
    }

    @Override
    public int saveArticleType(ArticleType articleType) {
        return articleTypeDAO.insert(articleType);
    }

    @Override
    public int updateArticleType(ArticleType articleType) {
        return articleTypeDAO.updateArticleType(articleType);
    }

    @Override
    public Map<String, Object> deleteArticleTypeById(String articleTypeId) {
        Map<String, Object> map = new HashMap<>();
        String[] ids = articleTypeId.split(",");
        int num = articleTypeDAO.deleteArticleTypeByIds(ids);
        map.put("delete", ids.length);
        map.put("success", num);
        if (ids.length != num) {
            map.put("msg", "请确认所选分类下文章数是否为0");
        }
        return map;
    }
}
