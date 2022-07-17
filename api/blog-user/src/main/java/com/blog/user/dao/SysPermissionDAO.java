package com.blog.user.dao;

import com.blog.common.entity.user.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

@Mapper
public interface SysPermissionDAO {
    Set<SysPermission> selectPermissionByRoleIds(@Param("roleIds") Set<Integer> roleIds);
}
