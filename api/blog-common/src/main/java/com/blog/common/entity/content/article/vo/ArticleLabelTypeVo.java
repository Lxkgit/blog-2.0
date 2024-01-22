package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleLabelType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.UpdateGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/17 14:44
 * @description:
 */

@Getter
@Setter
public class ArticleLabelTypeVo extends ArticleLabelType {

    /**
     * 标签分类id
     */
    @NotNull(message = "文章标签id不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "文章标签id值最小为1", groups = {UpdateGroup.class})
    private Integer id;

    @NotNull(message = "文章标签分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "文章标签分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "文章标签分类名称长度范围是1-20个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String typeName;

    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章标签分类id字符串", groups = {DeleteGroup.class})
    private String articleLabelTypeIds;

    private Integer value;

    private String label;

    private List<ArticleLabelVo> labelList;

    private BlogUser blogUser;
}