package com.blog.pi.netty.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blog.pi.dao.FileSyncDAO;
import com.blog.pi.entity.FileSync;
import com.blog.pi.entity.SensorData;
import com.blog.pi.ftp.FtpsUtil;
import com.blog.pi.netty.client.NettyClient;
import com.blog.pi.netty.dto.NettyPacket;
import com.blog.pi.netty.dto.NettyResponse;
import com.blog.pi.netty.dto.NettySyncBlogFile;
import com.blog.pi.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @description: 同步博客文章服务类
 * @Author: 308501
 * @date 2024/1/11 11:20
 */

@Component
public class SyncBlogFileService {

    @Resource
    private FtpsUtil ftpsUtil;

    @Resource
    private NettyClient nettyClient;

    @Resource
    private FileSyncDAO fileSyncDAO;

    /**
     * 下载服务器指定文件
     *
     * @param nettySyncBlogFile netty收到的消息
     * @param requestId 本次请求唯一编码
     */
    public void syncBlogFile(NettySyncBlogFile nettySyncBlogFile, String requestId) {
        if (nettySyncBlogFile.getSyncType().equals(0)) {
            String serviceFilePath = nettySyncBlogFile.getFilePath();
            String serviceFileName = nettySyncBlogFile.getFileName();

            String fileType = serviceFileName.substring(serviceFileName.lastIndexOf("."));
            String fileName = serviceFileName.substring(0, serviceFileName.lastIndexOf("."));

            String localFilePath = "D://testFtp";
            String localFileName = fileName + "_" + StringUtils.getRandomString(6) + fileType;
            boolean success = ftpsUtil.downloadFtpFile(serviceFilePath, serviceFileName, localFilePath, localFileName);

            // 响应服务端处理结果
            NettyResponse nettyResponse = new NettyResponse(success);
            NettyPacket<NettyResponse> nettyPacket = NettyPacket.buildResponse(requestId, nettyResponse);
            nettyClient.sendMsg(JSONObject.toJSONString(nettyPacket));

            if (success) {
                File file = new File(localFilePath + File.separatorChar + localFileName);
                FileSync fileSync = new FileSync();
                fileSync.setUserId(nettySyncBlogFile.getUserId());
                fileSync.setFileCode(nettySyncBlogFile.getFileCode());
                fileSync.setServiceFilePath(serviceFilePath);
                fileSync.setServiceFileName(serviceFileName);
                fileSync.setLocalFilePath(localFilePath);
                fileSync.setLocalFileName(localFileName);
                fileSync.setFileSize(file.length());
                fileSync.setCreateTime(new Date());
                fileSyncDAO.insert(fileSync);
            }

        } else if (nettySyncBlogFile.getSyncType().equals(1)) {
            QueryWrapper<FileSync> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("file_code", nettySyncBlogFile.getFileCode());
            queryWrapper.eq("user_id", nettySyncBlogFile.getUserId());
            List<FileSync> fileSyncList = fileSyncDAO.selectList(queryWrapper);
            if (!CollectionUtils.isEmpty(fileSyncList)) {
                FileSync fileSync = fileSyncList.get(0);
                boolean success = ftpsUtil.uploadFtpFile(fileSync.getLocalFilePath(), fileSync.getLocalFileName(), fileSync.getServiceFilePath(), fileSync.getServiceFileName());

                // 响应服务端处理结果
                NettyResponse nettyResponse = new NettyResponse(success);
                NettyPacket<NettyResponse> nettyPacket = NettyPacket.buildResponse(requestId, nettyResponse);
                nettyClient.sendMsg(JSONObject.toJSONString(nettyPacket));

                if (success) {
                    fileSyncDAO.deleteById(fileSync);
                }
            }
        }


    }
}
