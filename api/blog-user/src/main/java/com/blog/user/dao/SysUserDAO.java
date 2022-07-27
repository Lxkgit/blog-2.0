package com.blog.user.dao;

import com.blog.common.entity.user.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/6 16:56
 * @description:
 */

@Mapper
public interface SysUserDAO {

    BlogUser selectUserById(@Param("id") Integer id);
    BlogUser selectUserByUsername(@Param("username") String username);
    void insertUser(BlogUser blogUser);
    List<BlogUser> selectUserListByPage(@Param("startNum") Integer startNum, @Param("pageSize") Integer pageSize);
    int selectUserCount();
}
