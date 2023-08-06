package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.BlogData;

/**
 * @description: 博客数据统计表
 * @Author: lxk
 * @date 2023/7/10 16:58
 */

public interface BlogDataDAO extends BaseMapper<BlogData> {

    void updateBlogDataById(BlogData blogData);
}
