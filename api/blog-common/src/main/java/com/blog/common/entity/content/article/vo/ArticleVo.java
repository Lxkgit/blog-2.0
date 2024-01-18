package com.blog.common.entity.content.article.vo;


import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.content.article.ArticleLabel;
import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.valication.annotation.Equal;
import com.blog.common.valication.group.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/9 9:38
 * @description: 前端文章实体
 */

@Getter
@Setter
public class ArticleVo extends Article {

    /**
     * 文章id
     */
    @NotNull(message = "文章id不为空", groups = {UpdateGroup.class, SelectIdGroup.class})
    @Min(value = 1, message = "id值最小为1", groups = {UpdateGroup.class, SelectIdGroup.class})
    private Integer id;

    /**
     * 文章标题
     */
    @NotNull(message = "文章标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "文章标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 46, message = "文章标题长度范围是1-46个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String title;

    /**
     * 文章图片
     */
    @Size(max = 255, message = "图片链接长度不能超过255个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String contentImg;

    /**
     * 文章状态（2：置顶 1：发布 0：草稿）
     */
    @NotNull(message = "文章状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Equal(value = "0,1,2", message = "文章状态（2：置顶 1：发布 0：草稿）字段错误", groups = {AddGroup.class, UpdateGroup.class})
    private Integer articleStatus;

    /**
     * 文章分类 多个id使用 , 间隔
     */
    @Pattern(regexp = "^$|^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章分类字符串", groups = {AddGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^$|^[0-9]+$", message = "请输入正确的文章分类字符串", groups = {SelectListGroup.class})
    private String articleType;

    /**
     * 文章标签 多个id使用 , 间隔
     */
    @Pattern(regexp = "^$|^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章标签字符串", groups = {AddGroup.class, UpdateGroup.class})
    private String articleLabel;

    /**
     * 文章id列表 多个id使用 , 间隔
     */
    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章id字符串", groups = {DeleteGroup.class})
    private String articleIds;

    /**
     * 页大小
     */
    @NotNull(message = "分页查询页大小不能为空", groups = {SelectListGroup.class})
    @Max(value = 100, message = "分页大小最大为100", groups = {SelectListGroup.class})
    @Min(value = 5, message = "分页大小最小为5", groups = {SelectListGroup.class})
    private Integer pageSize;

    /**
     * 页数
     */
    @NotNull(message = "分页查询页数不能为空", groups = {SelectListGroup.class})
    @Min(value = 1, message = "分页查询页数最小1", groups = {SelectListGroup.class})
    private Integer pageNum;

    /**
     * 接口查询位置 0：首页 1：管理页面
     */
    @NotNull(message = "接口查询位置字段不能为空", groups = {SelectListGroup.class})
    @Equal(value = "0,1", message = "接口查询位置必须是0或1", groups = {SelectListGroup.class})
    private Integer type;

    /**
     * 查询文章所属用户 0：全部用户 x：指定用户
     */
    @Min(value = 0, message = "查询用户id值最小为0", groups = {SelectListGroup.class})
    private Integer selectUser;

    /**
     * 查询文章状态 0：草稿 1：已发布文章 2：置顶 3：已删除
     */
    @Pattern(regexp = "^[0-3](,[0-3]){0,3}|[0-3]$", message = "请输入正确的文章状态字符串", groups = {SelectListGroup.class})
    private String selectStatus;

    /**
     * 排序方式 0：置顶排序 1：更新时间排序
     */
    @Pattern(regexp = "^$|^[0-1](,[0-1])|[0-1]$", message = "请输入正确的排序方式", groups = {SelectListGroup.class})
    private String sortType;

    /**
     * 接口访问用户
     */
    private BlogUser blogUser;

    /**
     * 文章分类
     */
    private List<ArticleType> articleTypes;

    /**
     * 文章标签
     */
    private List<ArticleLabel> articleLabels;
}
