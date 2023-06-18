package com.blog.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.file.UploadLog;
import com.blog.common.entity.user.BlogUser;
import com.blog.common.entity.user.vo.SysUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: lxk
 * @date 2022/6/6 16:56
 * @description:
 */

@Mapper
public interface SysUserDAO extends BaseMapper<BlogUser> {

    BlogUser selectUserById(@Param("id") Integer id);
    BlogUser selectUserByUsername(@Param("username") String username);
    List<BlogUser> selectUserList();
    int selectUserCount();
    void updateUser(SysUserVo sysUserVo);
    void deleteUserRole(@Param("userId") Integer userId);
    void insertUserRole(@Param("roleIds") List<Integer> roleIds,@Param("userId") Integer userId);
}
