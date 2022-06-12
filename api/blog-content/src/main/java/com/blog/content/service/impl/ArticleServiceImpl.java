package com.blog.content.service.impl;

import com.blog.common.entity.content.Article;
import com.blog.common.entity.content.ArticleLabel;
import com.blog.common.entity.content.ArticleType;
import com.blog.common.entity.content.vo.ArticleVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.content.dao.ArticleDAO;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.service.ArticleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
        MyPage<ArticleVo> myPage = null;
        List<Article> articleList = articleDAO.selectArticleListByPage((page-1)*size, size);
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
            Integer count = articleDAO.selectArticleCount();
            myPage = MyPageUtils.pageUtil(articleVoList, page, size, count);
        } catch (Exception e){
            e.printStackTrace();
        }
        return myPage;
    }

    @Override
    public MyPage<ArticleVo> selectArticleListByPageAndUserId(Principal principal, int page, int size) {
        return null;
    }

    @Override
    public List<Article> selectArticleListAll() {
        return articleDAO.selectList(null);
    }
}
