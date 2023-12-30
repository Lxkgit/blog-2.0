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
import com.blog.common.enums.mq.RocketMQMsgEnum;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.common.util.MyPage;
import com.blog.common.util.MyPageUtils;
import com.blog.content.dao.ArticleDAO;
import com.blog.content.dao.ArticleLabelDAO;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.mq.MQProducerService;
import com.blog.content.mq.send.SendSystemData;
import com.blog.content.mq.send.SendUserData;
import com.blog.content.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private SendSystemData sendSystemData;

    @Resource
    private SendUserData sendUserData;

    /**
     * 查询文章列表
     *
     * @param articleVoParam 查询参数
     * @return
     */
    @Override
    public MyPage<ArticleVo> selectArticleListByPageAndUserId(ArticleVo articleVoParam) {
        MyPage<ArticleVo> myPage = null;
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();

        // 管理页面只查询当前用户文章，首页查询全部和指定用户文章
        if (articleVoParam.getType() == 1) {
            articleQueryWrapper.eq("user_id", articleVoParam.getBlogUser().getId());
        } else {
            if (articleVoParam.getSelectUser() != null && articleVoParam.getSelectUser() != 0) {
                articleQueryWrapper.eq("user_id", articleVoParam.getSelectUser());
            }
        }

        // 按照文章分类查询
        if (articleVoParam.getArticleType() != null && !articleVoParam.getArticleType().equals("")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(articleVoParam.getArticleType());
            ArticleType articleType = articleTypeDAO.selectById(Integer.parseInt(articleVoParam.getArticleType()));
            while (articleType.getParentId() != 0) {
                articleType = articleTypeDAO.selectById(articleType.getParentId());
                stringBuilder.insert(0, articleType.getId() + ",");
            }
            articleQueryWrapper.likeRight("article_type", stringBuilder.toString());
        }

        // 指定文章状态
        if (articleVoParam.getSelectStatus() != null && !articleVoParam.getSelectStatus().equals("")) {
            String[] status = articleVoParam.getSelectStatus().split(",");
            articleQueryWrapper.and((wrapper) -> {
                for (int i = 0; i < status.length; i++) {
                    if (i == 0) {
                        wrapper.eq("article_status", status[i]);
                    } else {
                        wrapper.or().eq("article_status", status[i]);
                    }
                }
            });
        }

        // 排序
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
        for (Article article : articlePage) {
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
            myPage = MyPageUtils.pageUtil(articleVoList, articlePage.getPageNum(), articlePage.getPageSize(), (int) articlePage.getTotal());
        } catch (Exception e) {
            log.info("分页查询文章报错, param:{}", JSON.toJSONString(articleVoParam));
            e.printStackTrace();
        }
        return myPage;
    }

    /**
     * 查询文章分类和标签
     *
     * @param article
     * @param articleVo
     */
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

    /**
     * 根据id查询文章
     *
     * @param articleId
     * @return
     */
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

    /**
     * 创建文章
     *
     * @param article
     * @return
     */
    @Override
    public int saveArticle(Article article) {
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        // 文章类型页面传入数据是类型树最底层节点id
        article.setArticleType(updateArticleType(article.getArticleType(), 1));
        updateArticleLabel(article.getArticleLabel(), 1);

        // 发送博客用户新增文章mq消息
        sendUserData.sendUserData(SendUserData.article, article.getUserId(), 1);

        // 发送博客系统新增文章mq消息
        sendSystemData.sendSystemData(SendSystemData.article, 1);
        articleDAO.insert(article);
        return article.getId();
    }

    /**
     * 更新文章接口
     *
     * @param blogUser 操作用户
     * @param article  文章数据
     * @return
     */
    @Override
    public int updateArticle(BlogUser blogUser, Article article) {
        ArticleBo articleBo = new ArticleBo();
        Article oldArticle = articleDAO.selectArticleById(article.getId());
        // 修改前文章分类-1
        updateArticleType(oldArticle.getArticleType(), -1);
        // 修改前文章标签对应文章-1
        updateArticleLabel(oldArticle.getArticleLabel(), -1);

        // 修改后文章分类+1
        article.setArticleType(updateArticleType(article.getArticleType(), 1));
        // 修改后文章标签对应文章+1
        updateArticleLabel(article.getArticleLabel(), 1);

        BeanUtils.copyProperties(article, articleBo);
        articleBo.setUpdateUserId(blogUser.getId());
        articleBo.setUpdateTime(new Date());
        articleDAO.updateArticle(articleBo);
        return article.getId();
    }

    /**
     * 修改文章分类下文章数量，返回文章分类全类型字符串（从根节点分类开始）
     *
     * @param articleTypes 文章分类
     * @param articleNum  文章数量变化
     * @return
     */
    private String updateArticleType(String articleTypes, Integer articleNum) {
        if (StringUtils.isNotBlank(articleTypes)) {
            String[] articleTypeArr = articleTypes.split(",");
            StringBuilder type = new StringBuilder(articleTypeArr[articleTypeArr.length-1]);
            ArticleType type1 = articleTypeDAO.selectArticleTypeById(Integer.parseInt(String.valueOf(type)));
            while (type1.getParentId() != 0) {
                type.insert(0, type1.getParentId() + ",");
                articleTypeDAO.updateArticleTypeNumById(type1.getId(), articleNum);
                type1 = articleTypeDAO.selectArticleTypeById(type1.getParentId());
            }
            articleTypeDAO.updateArticleTypeNumById(type1.getId(), articleNum);
            return type.toString();
        }
        return null;
    }

    private void updateArticleLabel(String articleLabel, Integer articleNum) {
        if (StringUtils.isNotBlank(articleLabel)) {
            String[] labels = articleLabel.split(",");
            for (String label : labels) {
                articleLabelDAO.updateArticleLabelNumById(Integer.parseInt(label), articleNum);
            }
        }
    }

    /**
     * 删除文章, 并非真正删除，修改文章状态为删除
     *
     * @param blogUser
     * @param articleIds
     * @return
     */
    @Override
    public Integer deleteArticle(BlogUser blogUser, String articleIds) {
        ArticleBo articleBo = new ArticleBo();
        articleBo.setUserId(blogUser.getId());
        articleBo.setArticleStatus(3);
        articleBo.setIds(articleIds.split(","));
        Integer deleteArticleNum = articleDAO.updateArticleStatus(articleBo);

        for (String id : articleBo.getIds()) {
            Article article = articleDAO.selectArticleById(Integer.parseInt(id));


            updateArticleType(article.getArticleType(), -1);

            updateArticleLabel(article.getArticleLabel(), -1);
        }

        // 发送博客用户删除文章mq消息
        sendUserData.sendUserData(SendUserData.article, blogUser.getId(), -deleteArticleNum);

        // 发送博客系统删除文章mq消息
        sendSystemData.sendSystemData(SendSystemData.article, -deleteArticleNum);

        return deleteArticleNum;

    }
}
