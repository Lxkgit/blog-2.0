package com.blog.file.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.ContentCount;
import com.blog.common.enums.mq.RocketMQMsgEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.file.dao.BlogDataDAO;
import com.blog.file.dao.ContentCountDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @description: rockerMQ消息消费类
 * @Author: 308501
 * @date 2023/12/26 10:47
 */

@Slf4j
@Service
public class RockerMQMsgConsumer {

    @Resource
    private BlogDataDAO blogDataDAO;

    @Resource
    private ContentCountDAO contentCountDAO;

    /**
     * 处理博客系统消息
     * BLOG_SYSTEM_DATA("BLOG_SYSTEM_DATA", "CONTENT", "博客系统内容消息"),
     * @param rocketMQMessage
     */
    public void dealBlogSystemData(RocketMQMessage rocketMQMessage) {
        BlogData blogData = JSON.parseObject(rocketMQMessage.getMessage(), BlogData.class);
        blogData.setId(1);
        if (rocketMQMessage.getMqMsgType().equals(RocketMQMsgEnum.ALL.getType())) {
            blogDataDAO.updateById(blogData);
        } else if (rocketMQMessage.getMqMsgType().equals(RocketMQMsgEnum.ADD.getType())) {
            blogDataDAO.updateBlogDataById(blogData);
        }
    }

    /**
     * 处理博客用户消息
     * BLOG_USER_DATA("BLOG_USER_DATA", "CONTENT" , "博客用户内容统计数据"),
     * @param rocketMQMessage
     */
    public void dealBlogUserData(RocketMQMessage rocketMQMessage) {

        if (rocketMQMessage.getMqMsgType().equals(RocketMQMsgEnum.ALL.getType())) {
            List<ContentCount> contentCountList = JSON.parseArray(rocketMQMessage.getMessage(), ContentCount.class);
            if (CollectionUtils.isNotEmpty(contentCountList)) {
                for (ContentCount contentCount : contentCountList) {
                    if (contentCountDAO.selectCountByUserId(contentCount.getUserId()) == 1) {
                        UpdateWrapper<ContentCount> updateWrapper = new UpdateWrapper<>();
                        updateWrapper.eq("user_id", contentCount.getUserId());
                        contentCountDAO.update(contentCount, updateWrapper);
                    } else {
                        contentCountDAO.insert(contentCount);
                    }
                }
            } else {
                contentCountDAO.delete(null);
            }

        } else if (rocketMQMessage.getMqMsgType().equals(RocketMQMsgEnum.ADD.getType())) {
            ContentCount contentCount = JSON.parseObject(rocketMQMessage.getMessage(), ContentCount.class);
            if (contentCountDAO.selectCountByUserId(contentCount.getUserId()) == 1) {
                contentCountDAO.updateContentCountByUserId(contentCount);
            } else {
                contentCountDAO.insert(contentCount);
            }

        }
    }

}
