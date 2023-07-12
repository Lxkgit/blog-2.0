package com.blog.gateway.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.common.entity.gateway.RequestLog;
import com.blog.common.enums.mq.RocketMQTopicEnum;
import com.blog.common.message.mq.RocketMQMessage;
import com.blog.gateway.dao.RequestLogDAO;
import com.blog.gateway.mq.MQProducerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @description: 定时更新博客接口数据
 * @Author: 308501
 * @date 2023/7/8 17:14
 */

@Component
public class ScheduleBlogLog {

    // 日志id
    private static Integer logId = -1;

    private static Integer ipCount = 0;

    @Resource
    private RequestLogDAO requestLogDAO;

    @Resource
    private MQProducerService mqProducerService;

    /**
     * 每分钟查询一次日志表数据，获取表中id大于当前id的数据
     */
    @Scheduled(fixedRate = 60000)
    public void scheduledTask() {
        QueryWrapper<RequestLog> visitWrapper = new QueryWrapper<>();
        visitWrapper.gt("id", logId);
        visitWrapper.orderByDesc("id");
        List<RequestLog> visitLogList = requestLogDAO.selectList(visitWrapper);

        QueryWrapper<RequestLog> ipWrapper = new QueryWrapper<>();
        ipWrapper.select("count(*) as count, request_ip as requestIp");
        ipWrapper.groupBy("request_ip");
        List<Map<String, Object>> ipMaps = requestLogDAO.selectMaps(ipWrapper);

        if (logId == -1) {
            mqProducerService.sendSyncOrderly(new RocketMQMessage<>(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTopic(),
                    RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTag(), 0, setMqMessage(visitLogList, ipMaps)));
        } else {
            BlogDataVo blogDataVo = setMqMessage(visitLogList, ipMaps);
            if (blogDataVo != null) {
                mqProducerService.sendSyncOrderly(new RocketMQMessage<>(RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTopic(),
                        RocketMQTopicEnum.BLOG_STATISTICS_OVERALL.getTag(), 1, blogDataVo));
            }
        }
    }

    private BlogDataVo setMqMessage(List<RequestLog> requestLogs, List<Map<String, Object>> ipMaps) {
        Integer nowIpCount = ipMaps.size();
        if ((requestLogs == null || requestLogs.size() ==0) && nowIpCount.equals(ipCount)) {
            return null;
        }

        BlogDataVo blogDataVo = new BlogDataVo();
        if (requestLogs != null && requestLogs.size() > 0) {
            logId = requestLogs.get(0).getId();
            blogDataVo.setVisits(requestLogs.size());
        }

        if (!nowIpCount.equals(ipCount)) {
            if (ipCount == 0) {
                blogDataVo.setIpCount(nowIpCount);
            } else {
                blogDataVo.setIpCount(nowIpCount - ipCount);
            }
            ipCount = nowIpCount;
        }
        return blogDataVo;
    }
}
