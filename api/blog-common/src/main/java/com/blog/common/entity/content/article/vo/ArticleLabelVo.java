package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.user.BlogUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: lxk
 * @date 2023/3/30 16:51
 * @description: 文章标签Vo类
 */

@Getter
@Setter
public class ArticleLabelVo extends ArticleLabel {

    BlogUser blogUser;

}
