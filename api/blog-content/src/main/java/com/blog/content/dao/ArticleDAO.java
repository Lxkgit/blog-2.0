package com.blog.content.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.content.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDAO extends BaseMapper<Article> {

    Integer selectArticleCount();
    List<Article> selectArticleListByPage(@Param("startNum")int startNum, @Param("pageSize") int pageSize);
    List<Article> selectArticleListByPageAndUserId(@Param("userId")int userId, @Param("startNum")int startNum, @Param("pageSize") int pageSize);

}
