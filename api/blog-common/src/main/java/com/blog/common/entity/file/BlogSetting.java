package com.blog.common.entity.file;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @description: 博客设置表
 * @Author: 308501
 * @date 2023/7/25 15:04
 */

@Data
public class BlogSetting {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 设置类型 0：全局设置 1：个人设置
    private String settingType;

    // 个人设置用户id
    private Integer userId;

    // 设置类型 0：分割线 1: 输入框 2：数字输入框 3：开关 4：长文本输入框 5：图片上传 6：多图片上传
    private Integer type;

    // 设置名称
    private String title;

    // JSON格式设置信息
    private String setting;
}
