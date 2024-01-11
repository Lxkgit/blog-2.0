package com.blog.pi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 博客文件数据同步表
 * @Author: 308501
 * @date 2024/1/11 14:39
 */

@Data
@TableName("blog_file_sync")
public class FileSync {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String fileCode;

    private String localFilePath;

    private String localFileName;

    private String serviceFilePath;

    private String serviceFileName;

    private Long fileSize;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
