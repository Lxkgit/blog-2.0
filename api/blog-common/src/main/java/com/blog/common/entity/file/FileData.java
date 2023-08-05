package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: BlogFile
 * @Author: 308501
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

}
