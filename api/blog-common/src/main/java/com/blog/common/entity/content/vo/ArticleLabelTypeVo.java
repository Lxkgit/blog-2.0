package com.blog.common.entity.content.vo;

import com.blog.common.entity.content.ArticleLabel;
import com.blog.common.entity.content.ArticleLabelType;
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

    List<ArticleLabel> labelList;
}