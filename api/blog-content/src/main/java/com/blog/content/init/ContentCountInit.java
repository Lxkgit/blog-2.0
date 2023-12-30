package com.blog.content.init;

import com.alibaba.fastjson.JSON;
import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.ContentCount;
import com.blog.common.enums.mq.RocketMQMsgEnum;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.content.dao.*;
import com.blog.content.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description: 博客内容统计初始化
 * @Author: lxk
 * @date 2023/6/29 11:06
 */

@Slf4j
@Configuration     //证明这个类是一个配置文件
@EnableScheduling  //启用定时器
public class ContentCountInit {

    // 发送文章
    private static final Integer article = 1;
    // 发送日记
    private static final Integer diary = 2;
    // 发送文档
    private static final Integer doc = 3;

    @Resource
    private ArticleDAO articleDAO;

    @Resource
    private DiaryDAO diaryDAO;

    @Resource
    private DocContentDAO docContentDAO;

    @Resource
    private ArticleLabelDAO articleLabelDAO;

    @Resource
    private MQProducerService mqProducerService;

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void initContent() {
        log.info("开始初始化博客内容数据 ... ");

        // 初始化系统消息数据
        initSystemData();

        initUserData();

    }

    /**
     * 初始化系统消息mq
     */
    private void initSystemData() {
        BlogData blogData = new BlogData();
        blogData.setArticleCount(articleDAO.selectArticleCount());
        blogData.setArticleLabelCount(articleLabelDAO.selectArticleLabelCount());
        blogData.setDocCount(docContentDAO.selectDocContentCount());
        blogData.setArticleTypeCount(0);
        blogData.setDocTypeCount(0);
        blogData.setDiaryCount(diaryDAO.selectDiaryCount());
        RocketMQMessage rocketMQMessage = new RocketMQMessage();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_SYSTEM_DATA.getTag());
        rocketMQMessage.setMessage(JSON.toJSONString(blogData));
        rocketMQMessage.setMqMsgType(RocketMQMsgEnum.ALL.getType());
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }

    /**
     * 初始化用户消息
     */
    private void initUserData() {
        List<ContentCount> contentCountList = new ArrayList<>();

        List<Map<String,Integer>> articleList = articleDAO.selectArticleCountGroupByUserId();
        setContentCountList(contentCountList, articleList, article);
        List<Map<String,Integer>> diaryList = diaryDAO.selectDiaryCountGroupByUserId();
        setContentCountList(contentCountList, diaryList, diary);
        List<Map<String,Integer>> docList = docContentDAO.selectDocCountGroupByUserId();
        setContentCountList(contentCountList, docList, doc);

        RocketMQMessage rocketMQMessage = new RocketMQMessage();
        rocketMQMessage.setTopic(RocketMQTopicEnum.BLOG_USER_DATA.getTopic());
        rocketMQMessage.setTag(RocketMQTopicEnum.BLOG_USER_DATA.getTag());
        rocketMQMessage.setMessage(JSON.toJSONString(contentCountList));
        rocketMQMessage.setMqMsgType(RocketMQMsgEnum.ALL.getType());
        mqProducerService.sendSyncOrderly(rocketMQMessage);
    }

    /**
     * 组装mq消息数据
     * @param contentCountList
     * @param mapList
     * @param type
     */
    private void setContentCountList(List<ContentCount> contentCountList, List<Map<String, Integer>> mapList, Integer type) {
        mapList.forEach(item -> {
            int userId = Integer.parseInt(String.valueOf(item.get("userId")));
            AtomicBoolean flag = new AtomicBoolean(false);
            contentCountList.forEach(contentCount -> {
                if (contentCount.getUserId().equals(userId)) {
                    flag.set(true);
                    setContentCount(type, item, contentCount);
                }
            });
            if (!flag.get()) {
                ContentCount contentCount = new ContentCount();
                contentCount.setUserId(userId);
                setContentCount(type, item, contentCount);
                contentCountList.add(contentCount);
            }
        });
    }

    private void setContentCount(Integer type, Map<String, Integer> item, ContentCount contentCount) {
        if (type.equals(article)) {
            contentCount.setArticleCount(Integer.parseInt(String.valueOf(item.get("count"))));
        } else if (type.equals(diary)) {
            contentCount.setDiaryCount(Integer.parseInt(String.valueOf(item.get("count"))));
        } else if (type.equals(doc)) {
            contentCount.setDocCount(Integer.parseInt(String.valueOf(item.get("count"))));
        }
    }
}
