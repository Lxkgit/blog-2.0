package com.blog.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blog.common.entity.user.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

@Mapper
public interface SysRoleDAO extends BaseMapper<SysRole> {

    Set<SysRole> selectRoleByUserId(@Param("userId")Integer id);
    int selectRoleCount();
    List<SysRole> selectRoleListByPage(@Param("startNum") Integer startNum, @Param("pageSize") Integer pageSize);
    int updateRole(SysRole sysRole);
    int deleteRoleByIds(@Param("ids") String[] ids);
}