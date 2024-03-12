package com.blog.pi.netty.dto;

import lombok.Data;

/**
 * @description: netty博客文件数据同步
 * @Author: 308501
 * @date 2024/1/11 10:07
 */

@Data
public class NettySyncBlogFile {

    /**
     * 文件所属用户id
     */
    private Integer userId;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件唯一标识码
     */
    private String fileCode;

    /**
     * 文件同步类型 0: 文件同步至远程 1: 文件下载到本地
     */
    private Integer syncType;

}
