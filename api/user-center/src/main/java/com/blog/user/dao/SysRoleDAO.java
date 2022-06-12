package com.blog.user.dao;

import com.blog.common.entity.user.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface SysRoleDAO {

    Set<SysRole> selectRoleByUserId(@Param("userId")Integer id);
}
