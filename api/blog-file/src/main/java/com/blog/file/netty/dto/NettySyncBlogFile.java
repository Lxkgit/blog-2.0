package com.blog.file.netty.dto;

import lombok.Data;

/**
 * @description: netty博客文件数据同步
 * @Author: 308501
 * @date 2024/1/11 10:07
 */

@Data
public class NettySyncBlogFile {

    private Integer userId;

    private String filePath;

    private String fileName;

    private String fileCode;

    // 文件同步类型 0: 文件同步至远程 1: 文件下载到本地
    private Integer syncType;

}
