package com.blog.common.entity.content.article.vo;

import com.blog.common.entity.content.article.ArticleType;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.valication.group.AddGroup;
import com.blog.common.valication.group.DeleteGroup;
import com.blog.common.valication.group.SelectListGroup;
import com.blog.common.valication.group.UpdateGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/14 16:35
 * @description: 前端文章类型实体
 */

@Getter
@Setter
public class ArticleTypeVo extends ArticleType {

    @NotNull(message = "文章分类id不能为空", groups = {UpdateGroup.class})
    @Min(value = 1, message = "文章分类id值最小为1", groups = {UpdateGroup.class})
    private Integer id;

    @NotNull(message = "文章分类父id不能为空", groups = {AddGroup.class, UpdateGroup.class, SelectListGroup.class})
    @Min(value = 0, message = "文章分类父id值最小为0", groups = {AddGroup.class, UpdateGroup.class, SelectListGroup.class})
    private Integer parentId;

    @NotNull(message = "文章分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @NotEmpty(message = "文章分类名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size(min = 1, max = 20, message = "文章分类长度范围是1-20个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String typeName;

    @Pattern(regexp = "^[0-9]+(,[0-9]+)+|[0-9]+$", message = "请输入正确的文章id字符串", groups = {DeleteGroup.class})
    private String articleTypeId;

    private String value;

    private String label;

    private BlogUser blogUser;

    private List<ArticleTypeVo> children;
}