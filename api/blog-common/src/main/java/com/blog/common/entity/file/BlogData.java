package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 博客数据实体类
 * @Author: 308501
 * @date 2023/7/10 15:48
 */

@Data
public class BlogData {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 博客部署时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deploymentTime;

    // 接口访问总次数
    private Integer visits;

    // 用户数量
    private Integer userCount;

    // 访问ip数
    private Integer ipCount;

    // 文章总数
    private Integer articleCount;

    // 文章分类数
    private Integer articleTypeCount;

    // 文章标签数
    private Integer articleLabelCount;

    // 文档总数
    private Integer docCount;

    // 文档分类数
    private Integer docTypeCount;

    // 日记总数
    private Integer diaryCount;

    // 图片总数
    private Integer imgCount;
}
