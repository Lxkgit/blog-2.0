package com.blog.file.service.impl;

import com.blog.common.entity.file.BlogData;
import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.file.dao.BlogDataDAO;
import com.blog.file.service.BlogDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 博客数据接口服务
 * @Author: lxk
 * @date 2023/7/14 11:35
 */

@Service
public class BlogDataServiceImpl implements BlogDataService {

    @Resource
    private BlogDataDAO blogDataDAO;

    @Override
    public BlogDataVo selectBlogData() {
        BlogData blogData = blogDataDAO.selectById(1);
        BlogDataVo blogDataVo = new BlogDataVo();
        BeanUtils.copyProperties(blogData, blogDataVo);
        return blogDataVo;
    }
}
