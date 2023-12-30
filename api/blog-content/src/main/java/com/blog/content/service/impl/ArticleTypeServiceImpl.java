package com.blog.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.content.article.vo.ArticleTypeVo;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.content.dao.ArticleTypeDAO;
import com.blog.content.feign.UserClient;
import com.blog.content.mq.MQProducerService;
import com.blog.content.service.ArticleTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: lxk
 * @date 2022/6/14 16:31
 * @description: 文章类型服务类
 */

@Service
public class ArticleTypeServiceImpl implements ArticleTypeService {

    @Resource
    private ArticleTypeDAO articleTypeDAO;

    @Resource
    private UserClient userClient;

    @Resource
    private MQProducerService mqProducerService;

    @Override
    public List<ArticleType> selectArticleTypeByParentId(String parentId) {
        return articleTypeDAO.selectArticleTypeByParentId(parentId);
    }

    @Override
    public List<ArticleType> selectArticleTypeById(Integer id) {
        List<ArticleType> articleTypeList = new ArrayList<>();
        ArticleType articleType = articleTypeDAO.selectArticleTypeById(id);
        articleTypeList.add(articleType);
        if (articleType.getParentId() != 0) {
            articleTypeList.add(articleTypeDAO.selectArticleTypeById(articleType.getParentId()));
        }
        Collections.sort(articleTypeList);
        return articleTypeList;
    }

    @Override
    public List<ArticleType> selectArticleTypeList() {
        return articleTypeDAO.selectList(null);
    }

    /**
     * 获取完整的树接口
     * @return
     */
    @Override
    public List<ArticleTypeVo> selectArticleTypeTree() {
        Map<Integer, BlogUser> blogUserMap = new HashMap<>();
        List<ArticleType> articleTypeList = articleTypeDAO.selectList(null);
        List<ArticleTypeVo> articleTypeVoList = new ArrayList<>();
        for (ArticleType articleType : articleTypeList) {
            ArticleTypeVo articleTypeVo = new ArticleTypeVo();
            BlogUser blogUser = blogUserMap.get(articleType.getCreateUser());
            if (blogUser == null) {
                blogUser = userClient.selectUserById(articleType.getCreateUser());
                blogUserMap.put(articleType.getCreateUser(), blogUser);
            }
            articleTypeVo.setBlogUser(blogUser);
            BeanUtils.copyProperties(articleType, articleTypeVo);
            articleTypeVo.setValue(String.valueOf(articleType.getId()));
            articleTypeVo.setLabel(articleType.getTypeName());
            articleTypeVoList.add(articleTypeVo);
        }
        for (ArticleTypeVo articleTypeVo : articleTypeVoList) {
            if (articleTypeVo.getParentId() != 0){
                articleTypeVoList.forEach(a -> {
                    if(a.getId() == articleTypeVo.getParentId()){
                        if (a.getChildren() == null) {
                            a.setChildren(new ArrayList<>());
                        }
                        a.getChildren().add(articleTypeVo);
                    }
                });
            }
        }
        articleTypeVoList.removeIf(articleTypeVo -> articleTypeVo.getParentId() != 0);
        return articleTypeVoList;
    }

    @Override
    public int saveArticleType(ArticleType articleType) {
        articleType.setCreateTime(new Date());
        articleType.setUpdateTime(new Date());
        int id = articleTypeDAO.insert(articleType);
        // 发送博客系统新增文章分类mq消息
        BlogDataVo blogDataVo = new BlogDataVo();
        blogDataVo.setArticleTypeCount(1);
        RocketMQMessage blogDataVoRocketMQMessage = new RocketMQMessage(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic(),
                RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag(), 1, JSON.toJSONString(blogDataVo));
        mqProducerService.sendSyncOrderly(blogDataVoRocketMQMessage);
        return id;
    }

    @Override
    public int updateArticleType(ArticleType articleType) {
        articleType.setUpdateTime(new Date());
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
        // 发送博客系统新增文章分类mq消息
        BlogDataVo blogDataVo = new BlogDataVo();
        blogDataVo.setArticleTypeCount(-num);
        RocketMQMessage blogDataVoRocketMQMessage = new RocketMQMessage(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic(),
                RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag(), 1, JSON.toJSONString(blogDataVo));
        mqProducerService.sendSyncOrderly(blogDataVoRocketMQMessage);
        return map;
    }
}
