package com.blog.common.entity.content.doc;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/6/20 9:56
 * @description: 文档内容
 */

@Data
public class DocContent {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer catalogId;

    private String docContentMd;

    private int browseCount;

    private int likeCount;

    private Date createTime;

    private Date updateTime;
}
