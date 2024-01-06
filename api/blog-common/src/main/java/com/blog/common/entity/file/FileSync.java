package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description: 文件同步数据库
 * @Author: 308501
 * @date 2024/1/5 16:05
 */

@Data
public class FileSync {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件所属用户
     */
    private Integer userId;


    /**
     * 文件存放本地目录
     */
    private String filePath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件唯一标识码
     */
    private String fileSn;

    /**
     * 文件当前存放服务器编码（mqtt客户端编码 ClientId ）
     */
    private String fileClient;



}
