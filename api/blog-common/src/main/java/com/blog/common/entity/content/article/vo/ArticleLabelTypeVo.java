package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.user.BlogUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/17 14:44
 * @description:
 */

@Getter
@Setter
public class ArticleLabelTypeVo extends ArticleLabelType {

    private Integer value;

    private String label;

    private List<ArticleLabelVo> labelList;

    private BlogUser blogUser;
}