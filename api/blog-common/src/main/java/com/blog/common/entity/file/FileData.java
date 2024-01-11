package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: BlogFile
 * @Author: lxk
 * @date 2023/8/2 16:07
 */

@Data
public class FileData {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件/目录所属用户
     */
    private Integer userId;

    /**
     * 文件/目录名称
     */
    private String name;

    /**
     * 文件/目录全路径
     */
    private String path;

    /**
     * 文件类型 0：目录 1：文件
     */
    private Integer type;

    /**
     * 文件大小（字节）
     */
    private Long fileSize;

    /**
     * 目录类型 0:本地目录 1:同步目录
     */
    private Integer dirType;

    /**
     * 同步文件位置状态 0:远程服务器 1:本地服务器 2:正在同步远程服务器 3:正在同步本地服务器
     */
    private Integer status;

    /**
     * 同步文件唯一编码
     */
    private String fileCode;

    /**
     * 文件创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
