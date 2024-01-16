package com.blog.pi.netty.dto;

import lombok.Data;

/**
 * @description: netty博客文件数据同步
 * @Author: 308501
 * @date 2024/1/11 10:07
 */

@Data
public class NettySyncBlogFile {

    private String filePath;

    private String fileName;

    private String fileCode;

    private Integer syncType;

}
