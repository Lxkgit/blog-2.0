package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.SelectIdGroup;
import com.blog.common.valication.group.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * @Author: lxk
 * @date 2023/3/30 16:51
 * @description: 文章标签Vo类
 */

@Getter
@Setter
public class ArticleLabelVo extends ArticleLabel {

    /**
     * 标签类型
     */
    @NotNull(message = "文章标签id不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "文章标签id值最小为1", groups = {UpdateGroup.class})
    private Integer id;

    /**
     * 标签类型
     */
    @NotNull(message = "文章标签分类字段不能为空", groups = {AddGroup.class, UpdateGroup.class, SelectIdGroup.class})
    @Min(value = 1, message = "文章分类字段值最小为1", groups = {AddGroup.class, UpdateGroup.class, SelectIdGroup.class})
    private Integer labelType;

    /**
     * 标签名称
     */
    @NotNull(message = "文章标签名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "文章标签名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "文章标签长度范围是1-20个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String labelName;

    /**
     * 删除标签id列表
     */
    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章id字符串", groups = {DeleteGroup.class})
    private String ids;

    BlogUser blogUser;

}
