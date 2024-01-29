package com.blog.pi.netty.listener;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blog.pi.enums.netty.NettyTopicEnum;
import com.blog.pi.netty.dto.NettySyncBlogFile;
import com.blog.pi.netty.enums.NettyPacketType;
import com.blog.pi.netty.event.NettyPacketEvent;
import com.blog.pi.netty.service.SyncBlogFileService;
import io.netty.channel.ChannelId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description:
 * @Author: 308501
 * @date 2024/1/6 15:52
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class NettyClientPacketListener implements ApplicationListener<NettyPacketEvent> {

    @Resource
    private SyncBlogFileService syncBlogFileService;

    @Async
    @Override
    public void onApplicationEvent(NettyPacketEvent event) {
        ChannelId channelId = (ChannelId) event.getSource();
        String nettyPacketType = event.getNettyPacket().getNettyPacketType();
        String requestId = event.getNettyPacket().getRequestId();
        String topic = event.getNettyPacket().getTopic();
        String username = event.getNettyPacket().getUsername();
        String registerId = event.getNettyPacket().getRegisterId();
        String data = event.getNettyPacket().getData().toString();
        log.info("channelId:【{}】 requestId:【{}】 topic:【{}】 username:【{}】 registerId:【{}】 data:{}", channelId, requestId, topic, username, registerId, data);
        if (nettyPacketType.equals(NettyPacketType.HEARTBEAT.getValue())) {
            log.info(data);
        } else if (nettyPacketType.equals(NettyPacketType.REQUEST.getValue())) {
            // 处理服务端请求
            // 处理文件下载同步
            if (topic.equals(NettyTopicEnum.BLOG_FILE_SYNC.getTopic())) {
                NettySyncBlogFile nettySyncBlogFile = JSON.parseObject(data, NettySyncBlogFile.class);
                syncBlogFileService.syncBlogFile(nettySyncBlogFile, requestId);
            }
        } else if (nettyPacketType.equals(NettyPacketType.RESPONSE.getValue())) {
            // 处理服务端数据响应


        } else {
            log.warn("unknown NettyPacketType!! channelId:{} event:{}", channelId, JSONObject.toJSONString(event));
        }
    }
}
