package com.blog.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.article.bo.ArticleBo;
import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.content.dao.ArticleDAO;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lxk
 * @date 2022/6/9 9:33
 * @description: 文章服务
 */
@Slf4j
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private UserClient userClient;

    @Resource
    private ArticleDAO articleDAO;

    @Resource
    private ArticleTypeDAO articleTypeDAO;

    @Resource
    private ArticleLabelDAO articleLabelDAO;

    @Override
    public MyPage<ArticleVo> selectArticleListByPage(ArticleVo articleVo) {
        return this.selectArticle(articleVo);
    }

    @Override
    public MyPage<ArticleVo> selectArticleListByPageAndUserId(ArticleVo articleVo) {
        return this.selectArticle(articleVo);
    }

    @Override
    public ArticleVo selectArticleById(int articleId) {
        Article article = articleDAO.selectArticleById(articleId);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        BlogUser blogUser = userClient.selectUserById(article.getUserId());
        articleVo.setBlogUser(blogUser);
        String[] types = article.getArticleType().split(",");
        List<ArticleType> articleTypeList = articleTypeDAO.selectArticleTypeByArray(types);
        articleVo.setArticleTypes(articleTypeList);
        String[] labels = article.getArticleLabel().split(",");
        List<ArticleLabel> articleLabelList = articleLabelDAO.selectArticleLabelByArray(labels);
        articleVo.setArticleLabels(articleLabelList);
        return articleVo;
    }

    @Override
    public int saveArticle(Article article){
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        return articleDAO.insert(article);
    }

    @Override
    public int updateArticle(BlogUser blogUser, Article article) {
        ArticleBo articleBo = new ArticleBo();
        article.setArticleType(updateArticleType(article.getArticleType()));
        BeanUtils.copyProperties(article, articleBo);
        articleBo.setUpdateUserId(blogUser.getId());
        articleBo.setUpdateTime(new Date());
        return articleDAO.updateArticle(articleBo);
    }

    private String updateArticleType(String articleType) {
        StringBuilder type = new StringBuilder(articleType);
        ArticleType type1 = articleTypeDAO.selectArticleTypeById(Integer.parseInt(articleType));
        while (type1.getParentId() != 0) {
            type.append(",").append(type1.getParentId());
            type1 = articleTypeDAO.selectArticleTypeById(type1.getParentId());
        }
        return type.reverse().toString();
    }

    @Override
    public Map<String, Integer> deleteArticle(BlogUser blogUser, String article) {
        HashMap<String, Integer> map = new HashMap<>();
        ArticleBo articleBo = new ArticleBo();
        articleBo.setUserId(blogUser.getId());
        articleBo.setIds(article.split(","));
        map.put("delete", articleBo.getIds().length);
        map.put("success", articleDAO.deleteArticle(articleBo));
        return map;
    }

    /**
     * 查询文章内部方法
     * userId = 0 时查询全部文章
     * @param articleVoParam
     * @return
     */
    private MyPage<ArticleVo> selectArticle(ArticleVo articleVoParam){
        MyPage<ArticleVo> myPage = null;
        PageHelper.startPage(articleVoParam.getPageNum(), articleVoParam.getPageSize());
        Page<Article> articlePage = (Page<Article>) articleDAO.selectArticleListByPage(articleVoParam);
        List<ArticleVo> articleVoList = new ArrayList<>();
        Map<Integer, BlogUser> userMap = new HashMap<>();
        for (Article article : articlePage){
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);

            if (userMap.containsKey(article.getUserId())) {
                articleVo.setBlogUser(userMap.get(article.getUserId()));
            } else {
                BlogUser blogUser = userClient.selectUserById(article.getUserId());
                userMap.put(article.getUserId(), blogUser);
                articleVo.setBlogUser(blogUser);
            }

            if (article.getArticleType() != null && !article.getArticleType().equals("")) {
                String[] types = article.getArticleType().split(",");
                List<ArticleType> articleTypeList = articleTypeDAO.selectArticleTypeByArray(types);
                articleVo.setArticleTypes(articleTypeList);
            }
            if (article.getArticleLabel() != null && !article.getArticleLabel().equals("")) {
                String[] labels = article.getArticleLabel().split(",");
                List<ArticleLabel> articleLabelList = articleLabelDAO.selectArticleLabelByArray(labels);
                articleVo.setArticleLabels(articleLabelList);
            }

            articleVoList.add(articleVo);
        }
        try {
            myPage = MyPageUtils.pageUtil(articleVoList, articlePage.getPageNum(), articlePage.getPageSize(), (int)articlePage.getTotal());
        } catch (Exception e){
            log.info("分页查询文章报错, param:{}", JSON.toJSONString(articleVoParam));
            e.printStackTrace();
        }
        return myPage;
    }
}
