package com.blog.file.netty.schedule;

import com.alibaba.fastjson.JSONObject;
import com.blog.common.constant.ShellCommand;
import com.blog.common.util.ShellUtil;
import com.blog.file.netty.common.NettyConstant;
import com.blog.file.netty.dto.NettyPacket;
import com.blog.file.netty.dto.NettySyncBlogFile;
import com.blog.file.netty.enums.NettyPacketType;
import com.blog.file.netty.enums.NettyTopicEnum;
import com.blog.file.netty.service.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @description: 博客数据定时同步
 * @Author: 308501
 * @date 2024/1/11 10:26
 */

@Slf4j
@Configuration     //证明这个类是一个配置文件
@EnableScheduling  //启用定时器
public class SyncBlogFileSchedule {

    @Resource
    private NettyServer nettyServer;


//    @PostConstruct  // 项目启动时执行这个方法
    @Scheduled(cron = "0 0 0 * * ?")
    public void initFile() {
        log.info("文件同步");
        if (!ShellUtil.shell(ShellCommand.exportBlogZip)) {
            // 脚本执行失败停止执行
            return;
        }

        NettySyncBlogFile nettySyncBlogFile = new NettySyncBlogFile();
        nettySyncBlogFile.setSyncType(2);
        nettySyncBlogFile.setFileCode("blog.zip");
        nettySyncBlogFile.setFileName("blog.zip");
        nettySyncBlogFile.setFilePath("/");

        NettyPacket<NettySyncBlogFile> nettyResponse = NettyPacket.buildRequest(nettySyncBlogFile);
        nettyResponse.setNettyPacketType(NettyPacketType.REQUEST.getValue());
        nettyResponse.setTopic(NettyTopicEnum.BLOG_FILE_SYNC.getTopic());

        nettyServer.channelWriteByRegisterId(NettyConstant.NETTY_CLIENT1, JSONObject.toJSONString(nettyResponse));

    }


}
