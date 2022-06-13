package com.blog.common.entity.content;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: lxk
 * @date 2022/6/8 19:48
 * @description: 文章实体类
 */

@Data
public class Article {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String title;
    private String contentMd;
    private String contentHtml;
    private String articleType;
    private String articleLabel;
    private int articleStatus;
    private int browseCount;
    private int likeCount;
    private Date createTime;
    private Date updateTime;
}
