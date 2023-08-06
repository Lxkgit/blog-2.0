package com.blog.file.service;

import com.blog.common.entity.file.vo.BlogSettingVo;
import com.blog.common.entity.user.BlogUser;

import java.util.List;

/**
 * @description: 博客设置
 * @Author: lxk
 * @date 2023/7/25 17:26
 */

public interface BlogSettingService {

    BlogSettingVo selectBlogSettingById(Integer id);

    List<BlogSettingVo> selectBlogSetting(BlogUser blogUser, String type);

    void updateBlogSetting(BlogUser blogUser, BlogSettingVo blogSettingVo);
}
