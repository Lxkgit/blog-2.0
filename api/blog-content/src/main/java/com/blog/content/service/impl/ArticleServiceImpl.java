package com.blog.content.service.impl;

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
import com.blog.content.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lxk
 * @date 2022/6/9 9:33
 * @description: 文章服务
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleDAO articleDAO;

    @Resource
    private ArticleTypeDAO articleTypeDAO;

    @Resource
    private ArticleLabelDAO articleLabelDAO;

    @Override
    public MyPage<ArticleVo> selectArticleListByPage(int page, int size) {
        return this.selectArticle(page, size, 0);
    }

    @Override
    public MyPage<ArticleVo> selectArticleListByPageAndUserId(int page, int size, int userId) {
        return this.selectArticle(page, size, userId);
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
        BeanUtils.copyProperties(article, articleBo);
        articleBo.setUpdateUserId(blogUser.getId());
        articleBo.setUpdateTime(new Date());
        return articleDAO.updateArticle(articleBo);
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
     * @param page 查询文章页数
     * @param size 查询文章每页文章数
     * @param userId 用户Id
     * @return
     */
    private MyPage<ArticleVo> selectArticle(int page, int size, int userId){
        MyPage<ArticleVo> myPage = null;
        List<Article> articleList = articleDAO.selectArticleListByPage((page-1)*size, size, userId);
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList){
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(article, articleVo);

            String[] types = article.getArticleType().split(",");
            List<ArticleType> articleTypeList = articleTypeDAO.selectArticleTypeByArray(types);
            articleVo.setArticleTypes(articleTypeList);

            String[] labels = article.getArticleLabel().split(",");
            List<ArticleLabel> articleLabelList = articleLabelDAO.selectArticleLabelByArray(labels);
            articleVo.setArticleLabels(articleLabelList);

            articleVoList.add(articleVo);
        }
        try {
            Integer count = articleDAO.selectArticleCount(userId);
            myPage = MyPageUtils.pageUtil(articleVoList, page, size, count);
        } catch (Exception e){
            e.printStackTrace();
        }
        return myPage;
    }
}
