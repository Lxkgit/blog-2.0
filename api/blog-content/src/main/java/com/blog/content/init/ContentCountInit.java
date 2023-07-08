package com.blog.content.init;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.diary.Diary;
import com.blog.common.entity.content.doc.DocCatalog;
import com.blog.common.entity.file.vo.ContentCountVo;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.content.dao.ArticleDAO;
import com.blog.content.dao.DiaryDAO;
import com.blog.content.dao.DocCatalogDAO;
import com.blog.content.mq.MQProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @description: 博客内容统计初始化
 * @Author: 308501
 * @date 2023/6/29 11:06
 */

@Slf4j
@Component
public class ContentCountInit implements ApplicationRunner {

    @Resource
    private ArticleDAO articleDAO;

    @Resource
    private DiaryDAO diaryDAO;

    @Resource
    private DocCatalogDAO docCatalogDAO;

    @Resource
    private MQProducerService mqProducerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("开始初始化博客内容数据 ... ");
        List<ContentCountVo> countVoList = new ArrayList<>();
        List<Map<String, Object>> articleMaps = initArticleCount();
        List<Map<String, Object>> diaryMaps = initDiaryCount();
        List<Map<String, Object>> docMaps = initDocCount();
        for (Map<String, Object> item : articleMaps) {
            log.info(articleMaps.toString());
            ContentCountVo countVo = new ContentCountVo();
            countVo.setUserId(((Long) item.get("userId")).intValue());
            countVo.setArticleCount(((Long) item.get("count")).intValue());
            countVoList.add(countVo);
        }

        for (Map<String, Object> item : diaryMaps) {
            AtomicReference<Boolean> flag = new AtomicReference<>(false);
            countVoList.forEach(contentCountVo -> {
                if (contentCountVo.getUserId() ==  item.get("userId") ) {
                    flag.set(true);
                    contentCountVo.setDiaryCount(((Long) item.get("count")).intValue());
                }
            });
            if (!flag.get()) {
                ContentCountVo countVo = new ContentCountVo();
                countVo.setUserId((Integer) item.get("userId"));
                countVo.setDiaryCount(((Long) item.get("count")).intValue());
                countVoList.add(countVo);
            }
        }

        for (Map<String, Object> item : docMaps) {
            AtomicReference<Boolean> flag = new AtomicReference<>(false);
            countVoList.forEach(contentCountVo -> {
                if (contentCountVo.getUserId() ==  item.get("userId") ) {
                    flag.set(true);
                    contentCountVo.setDocCount(((Long) item.get("count")).intValue());
                }
            });
            if (!flag.get()) {
                ContentCountVo countVo = new ContentCountVo();
                countVo.setUserId((Integer) item.get("userId"));
                countVo.setDocCount(((Long) item.get("count")).intValue());
                countVoList.add(countVo);
            }
        }
        Collections.sort(countVoList);
        for (ContentCountVo countVo : countVoList) {
            RocketMQMessage<ContentCountVo> rocketMQMessage = new RocketMQMessage<>();
            rocketMQMessage.setTopic(RocketMQTopicEnum.MQ_DATE_STATISTICS.getTopic());
            rocketMQMessage.setTag(RocketMQTopicEnum.MQ_DATE_STATISTICS.getTag());
            rocketMQMessage.setMessage(countVo);
            rocketMQMessage.setMqMsgType(0);
            mqProducerService.sendSyncOrderly(rocketMQMessage);

        }
    }

    /**
     * 初始化统计文章数量
     */
    private List<Map<String, Object>> initArticleCount() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("count(*) as count, user_id as userId");
        wrapper.groupBy("user_id");
        return articleDAO.selectMaps(wrapper);
    }

    /**
     * 初始化日记统计数量
     */
    private List<Map<String, Object>> initDiaryCount() {
        QueryWrapper<Diary> wrapper = new QueryWrapper<>();
        wrapper.select("count(*) as count, user_id as userId");
        wrapper.groupBy("user_id");
        return diaryDAO.selectMaps(wrapper);
    }

    /**
     * 初始化文档统计数量
     */
    private List<Map<String, Object>> initDocCount() {
        QueryWrapper<DocCatalog> wrapper = new QueryWrapper<>();
        wrapper.select("count(*) as count, user_id as userId");
        wrapper.eq("doc_type", 1);
        wrapper.groupBy("user_id");
        return docCatalogDAO.selectMaps(wrapper);
    }
}
