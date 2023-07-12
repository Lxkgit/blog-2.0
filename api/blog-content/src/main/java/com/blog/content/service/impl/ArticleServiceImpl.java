package com.blog.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.article.bo.ArticleBo;
import com.blog.common.entity.content.article.vo.ArticleVo;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.content.dao.ArticleDAO;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.mq.MQProducerService;
import com.blog.content.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    @Resource
    private MQProducerService mqProducerService;

    @Override
    public MyPage<ArticleVo> selectArticleListByPageAndUserId(ArticleVo articleVoParam) {
        MyPage<ArticleVo> myPage = null;
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        if (articleVoParam.getType() == 1) {
            articleQueryWrapper.eq("user_id", articleVoParam.getBlogUser().getId());
        } else {
            if (articleVoParam.getSelectUser() != null && articleVoParam.getSelectUser() != 0) {
                articleQueryWrapper.eq("user_id", articleVoParam.getSelectUser());
            }
        }
        if (articleVoParam.getArticleType() != null && !articleVoParam.getArticleType().equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(articleVoParam.getArticleType());
            ArticleType articleType = articleTypeDAO.selectById(Integer.parseInt(articleVoParam.getArticleType()));
            while (articleType.getParentId()!=0) {
                articleType = articleTypeDAO.selectById(articleType.getParentId());
                stringBuilder.insert(0, articleType.getId()+",");
            }
            articleQueryWrapper.likeRight("article_type", stringBuilder.toString());
        }
        if (articleVoParam.getSelectStatus() != null && !articleVoParam.getSelectStatus().equals("")) {
            String[] status = articleVoParam.getSelectStatus().split(",");
            articleQueryWrapper.and((wrapper) -> {
                for (int i=0; i<status.length; i++) {
                    if (i==0) {
                        wrapper.eq("article_status", status[i]);
                    } else {
                        wrapper.or().eq("article_status", status[i]);
                    }
                }
            });
        }
        if (articleVoParam.getSortType() != null && !articleVoParam.getSortType().equals("")) {
            List<String> sortList = Arrays.asList(articleVoParam.getSortType().split(","));
            if (sortList.contains("0")) {
                articleQueryWrapper.orderByDesc("article_status");
            }
            if (sortList.contains("1")) {
                articleQueryWrapper.orderByDesc("update_time");
            }
        }
        PageHelper.startPage(articleVoParam.getPageNum(), articleVoParam.getPageSize());
        Page<Article> articlePage = (Page<Article>) articleDAO.selectList(articleQueryWrapper);
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
            setArticleTypeAndLabel(article, articleVo);
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

    private void setArticleTypeAndLabel(Article article, ArticleVo articleVo) {
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
    }

    @Override
    public ArticleVo selectArticleById(int articleId) {
        Article article = articleDAO.selectArticleById(articleId);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        BlogUser blogUser = userClient.selectUserById(article.getUserId());
        articleVo.setBlogUser(blogUser);
        setArticleTypeAndLabel(article, articleVo);
        return articleVo;
    }

    @Override
    public int saveArticle(Article article){
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        if (article.getArticleType() != null && !article.getArticleType().equals("")) {
            article.setArticleType(updateArticleType(article.getArticleType()));
        }

        // 发送博客用户新增文章mq消息
        ContentCountVo contentCountVo = new ContentCountVo();
        contentCountVo.setUserId(article.getUserId());
        contentCountVo.setArticleCount(1);
        RocketMQMessage<ContentCountVo> countVoRocketMQMessage = new RocketMQMessage<>(RocketMQTopicEnum.MQ_DATE_STATISTICS.getTopic(),
                RocketMQTopicEnum.MQ_DATE_STATISTICS.getTag(), 1, contentCountVo);
        mqProducerService.sendSyncOrderly(countVoRocketMQMessage);

        // 发送博客系统新增文章mq消息
        BlogDataVo blogDataVo = new BlogDataVo();
        blogDataVo.setArticleCount(1);
        RocketMQMessage<BlogDataVo> blogDataVoRocketMQMessage = new RocketMQMessage<>(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTopic(),
                RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTag(), 1, blogDataVo);
        mqProducerService.sendSyncOrderly(blogDataVoRocketMQMessage);
        return articleDAO.insert(article);
    }

    @Override
    public int updateArticle(BlogUser blogUser, Article article) {
        ArticleBo articleBo = new ArticleBo();
        if (article.getArticleType() != null && !article.getArticleType().equals("")) {
            article.setArticleType(updateArticleType(article.getArticleType()));
        }
        BeanUtils.copyProperties(article, articleBo);
        articleBo.setUpdateUserId(blogUser.getId());
        articleBo.setUpdateTime(new Date());
        return articleDAO.updateArticle(articleBo);
    }

    private String updateArticleType(String articleType) {
        StringBuilder type = new StringBuilder(articleType);
        ArticleType type1 = articleTypeDAO.selectArticleTypeById(Integer.parseInt(articleType));
        while (type1.getParentId() != 0) {
            type.insert(0, type1.getParentId() + ",");
            type1 = articleTypeDAO.selectArticleTypeById(type1.getParentId());
        }
        return type.toString();
    }

    @Override
    public Map<String, Integer> deleteArticle(BlogUser blogUser, String article) {
        HashMap<String, Integer> map = new HashMap<>();
        ArticleBo articleBo = new ArticleBo();
        articleBo.setUserId(blogUser.getId());
        articleBo.setIds(article.split(","));
        map.put("delete", articleBo.getIds().length);
        map.put("success", articleDAO.deleteArticle(articleBo));

        // 发送博客用户删除文章mq消息
        ContentCountVo contentCountVo = new ContentCountVo();
        contentCountVo.setUserId(blogUser.getId());
        contentCountVo.setArticleCount(-article.split(",").length);
        RocketMQMessage<ContentCountVo> contentCountVoRocketMQMessage = new RocketMQMessage<>(RocketMQTopicEnum.MQ_DATE_STATISTICS.getTopic(),
                RocketMQTopicEnum.MQ_DATE_STATISTICS.getTag(), 1, contentCountVo);
        mqProducerService.sendSyncOrderly(contentCountVoRocketMQMessage);

        // 发送博客系统删除文章mq消息
        BlogDataVo blogDataVo = new BlogDataVo();
        blogDataVo.setArticleCount(-article.split(",").length);
        RocketMQMessage<BlogDataVo> blogDataVoRocketMQMessage = new RocketMQMessage<>(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTopic(),
                RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTag(), 1, blogDataVo);
        mqProducerService.sendSyncOrderly(blogDataVoRocketMQMessage);
        return map;
    }
}
