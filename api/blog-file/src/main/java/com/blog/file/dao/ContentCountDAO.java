package com.blog.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.article.Article;
import com.blog.common.entity.file.ContentCount;
import org.apache.ibatis.annotations.Param;

/**
 * @description: 用户内容数量统计表
 * @Author: lxk
 * @date 2023/6/28 17:23
 */

public interface ContentCountDAO extends BaseMapper<ContentCount> {

    Integer selectCountByUserId(@Param("userId")int userId);
    void updateContentCountByUserId(ContentCount contentCount);
}
