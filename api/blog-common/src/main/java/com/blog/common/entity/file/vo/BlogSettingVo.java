package com.blog.common.entity.file.vo;

import com.blog.common.entity.file.BlogSetting;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @description: 博客设置Vo类
 * @Author: lxk
 * @date 2023/7/25 15:18
 */

@Setter
@Getter
public class BlogSettingVo extends BlogSetting {

    // 1: 输入框 4：长文本输入框
    private String value;

    // 3：开关
    private Boolean bool;

    // 2：数字输入框 5：图片上传 6：多图片上传
    private Integer num;

    // 5：图片上传 6：多图片上传
    private List<String> valueList;
}
