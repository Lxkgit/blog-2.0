package com.blog.file.service.impl;

import com.blog.common.entity.file.vo.BlogDataVo;
import com.blog.file.dao.BlogDataDAO;
import com.blog.file.service.BlogDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description: 博客数据接口服务
 * @Author: 308501
 * @date 2023/7/14 11:35
 */

@Service
public class BlogDataServiceImpl implements BlogDataService {

    @Resource
    private BlogDataDAO blogDataDAO;

    @Override
    public BlogDataVo selectBlogData() {
        return (BlogDataVo) blogDataDAO.selectById(1);
    }
}
